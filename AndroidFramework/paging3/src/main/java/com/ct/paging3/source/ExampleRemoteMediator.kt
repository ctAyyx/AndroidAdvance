package com.ct.paging3.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ct.paging3.db.CategoryDao
import com.ct.paging3.net.ServiceApi
import com.ct.paging3.vo.GirlList

//@ExperimentalPagingApi
@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator(private val serviceApi: ServiceApi, private val dao: CategoryDao) :
    RemoteMediator<Int, GirlList>() {
    private var page = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GirlList>
    ): MediatorResult {

        //确定Key
        Log.e("TAG", "RemoteMediator:LoadType =${loadType}")
        val loadKey = when (loadType) {
            //刷新数据
            LoadType.REFRESH -> {
                //Log.e("TAG", "初始的缓存数据:${state.pages}")
                null
            }

            //追加数据
            LoadType.APPEND -> {
                //Log.e("TAG", "追加的缓存数据:${state.pages}")
            }

            //预加载数据
            //Paging预加载数据 第一次执行会有这个事件 已经缓存的数据
            LoadType.PREPEND -> {
                //Log.e("TAG", "预加载数据:${state.pages}")

                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        return try {
            //val page = if (state.pages.isEmpty()) 1 else state.pages.last().nextKey ?: 1

            val response = serviceApi.getGirlList(page++, 10)
            val newData = response.data.map {
                GirlList(
                    id = it.id,
                    author = it.author,
                    imgUrl = it.images[0],
                    title = it.title
                )
            }
            if (loadType == LoadType.PREPEND)
                dao.clearAll()
            dao.insertAll(newData)
            MediatorResult.Success(endOfPaginationReached = newData.isEmpty())
        } catch (e: Exception) {
            Log.e("TAG", "RemoteMediator:LoadType =${loadType} -- ${e}")
            MediatorResult.Error(e)
        }

    }
}