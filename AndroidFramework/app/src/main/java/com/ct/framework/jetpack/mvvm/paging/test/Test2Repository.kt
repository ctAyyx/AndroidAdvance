package com.ct.framework.jetpack.mvvm.paging.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ct.framework.jetpack.dto.BaseResponse
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.jetpack.mvvm.base.AppExecutors
import com.ct.framework.jetpack.mvvm.base.BaseResource
import com.ct.framework.jetpack.mvvm.paging.BdBoundaryCallback
import com.ct.framework.jetpack.mvvm.paging.NetworkBoundResource02
import com.ct.framework.jetpack.mvvm.paging.PagingResource
import com.ct.framework.jetpack.mvvm.paging.Resource02
import com.ct.framework.jetpack.net.ServiceApi
import com.ct.framework.jetpack.room.dao.GirlDao
import com.ct.framework.jetpack.source.CategoryDataSourceFactory
import com.ct.framework.jetpack.vo.GirlList

class Test2Repository(private val serviceApi: ServiceApi) {

    fun getGirlList(): Resource02<PagedList<GirlList>> {
        val factory = CategoryDataSourceFactory(serviceApi = serviceApi)
//        val request = object : NetworkBoundResource02<PagedList<GirlList>, PagedList<GirlList>>(
//            AppExecutors()
//        ) {
//            override fun createCall(): LiveData<PagedList<GirlList>> {
//                return factory.toLiveData(pageSize = 10)
//            }
//
//            override fun processResponse(response: PagedList<GirlList>?): PagedList<GirlList> =
//                response!!
//
//        }
        val data = factory.toLiveData(pageSize = 10)
        return Resource02(data = data,
            networkState = factory.dataSource.switchMap { it.networkState },
            refreshState = factory.dataSource.switchMap { it.initialLoad },
            refresh = { factory.dataSource.value?.invalidate() },
            retry = { factory.dataSource.value?.retryAllFailed() }
        )
    }


    fun getGirlListByDB(dao: GirlDao): Resource02<PagedList<GirlList>> {


        val factory = CategoryDataSourceFactory(serviceApi = serviceApi)
        val request = object : NetworkBoundResource02<PagedList<GirlList>, PagedList<GirlList>>(
            AppExecutors()
        ) {
            override fun createCall(): LiveData<PagedList<GirlList>> {
                return factory.toLiveData(pageSize = 10)
            }

            override fun processResponse(response: PagedList<GirlList>?): PagedList<GirlList> =
                response!!

            override fun useDB() = true


            override fun loadFromDb(): LiveData<PagedList<GirlList>> {
                Log.e("TAG", "充数据库获取数据")
                return dao.getAll()
                    .toLiveData(pageSize = 10, boundaryCallback = BdBoundaryCallback())
            }

            override fun saveCallResult(data: PagedList<GirlList>) {
                Log.e("TAG", "保存数据到数据库")
                dao.insert(data)
            }

            override fun shouldFetch(data: PagedList<GirlList>?): Boolean {
                Log.e("TAG", "---------------->")
                return true // data?.isNullOrEmpty() ?: true
            }

        }
        val data = request.asLiveData()
        return Resource02(data = data,
            networkState = factory.dataSource.switchMap { it.networkState },
            refreshState = factory.dataSource.switchMap { it.initialLoad },
            refresh = { factory.dataSource.value?.invalidate() },
            retry = { factory.dataSource.value?.retryAllFailed() }
        )
    }

    private var page = 2
    fun getGirlListByDB2(dao: GirlDao): Resource02<PagedList<GirlList>> {
        val request = object :
            PagingResource<GirlList, BaseResponse<List<Category>>>(AppExecutors()) {
            override fun shouldFetchFromRemote(itemAtEnd: GirlList): Boolean {
                return true
            }

            override fun loadFromDB(): DataSource.Factory<Int, GirlList> {
                return dao.getAll()
            }

            override fun createCall(): BaseResponse<List<Category>> {
                val call = serviceApi.getGirlList(page++, 10)
                val response = call.execute()
                return response.body()!!
            }

            override fun progressResponse(response: BaseResponse<List<Category>>): List<GirlList> {
                return response.data.map {
                    GirlList(
                        id = it.id,
                        title = it.title,
                        imgUrl = it.url,
                        author = it.author
                    )
                }
            }

            override fun saveToDB(response: List<GirlList>) {
                dao.insert(response)
            }

        }


        return Resource02(data = request.asLiveData())

    }
}