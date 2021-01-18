package com.ct.paging3.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ct.paging3.db.CategoryDao
import com.ct.paging3.net.ServiceApi
import com.ct.paging3.vo.GirlList
import kotlinx.coroutines.delay

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
        Log.e(
            "TAG",
            "RemoteMediator:LoadType =${loadType}   $page===>${state.pages.size}:${state.pages}"
        )

        val loadKey = when (loadType) {
            //刷新数据
            //如果我们不需要每次都刷新数据呢,而是继续上一次的分页???
            //那么我们就需要获取数据所有数据数量 来确定起始分页
            //或者 在存入数据库的时候 每个实体保存对应的分页
            LoadType.REFRESH -> {
                //Log.e("TAG", "初始的缓存数据:${state.pages}")
//                val count = dao.getCategorySize()
//                if (count > 0) {
//                    page = count / 10 + 1
//                    return MediatorResult.Success(endOfPaginationReached = false)
//                }
                page = 1
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
            Log.e("TAG", "我们开始请求数据$page")
            val response = serviceApi.getGirlList(page++, 10)
            delay(10_000L)
            val newData = response.data.map {
                GirlList(
                    id = it.id,
                    author = it.author,
                    imgUrl = it.images[0],
                    title = it.title
                )
            }
            //如果我们需要刷新数据呢???
            //如果我们需要每次初始化的时候重新加载数据 就需要在状态是 REFRESH 的时候移除数据库所有数据 并重新添加
            if (loadType == LoadType.REFRESH)
                dao.clearAll()
            dao.insertAll(newData)
//            if (page == 3)
//                MediatorResult.Error(NullPointerException("这是故意制造的异常...$page"))
//            else
                MediatorResult.Success(endOfPaginationReached = newData.isEmpty())
        } catch (e: Exception) {
            Log.e("TAG", "RemoteMediator:LoadType =${loadType} -- $e")
            MediatorResult.Error(e)
        }

    }
}