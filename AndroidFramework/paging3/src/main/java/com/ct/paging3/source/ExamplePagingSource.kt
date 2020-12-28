package com.ct.paging3.source

import android.util.Log
import androidx.paging.PagingSource
import com.ct.paging3.dto.Category
import com.ct.paging3.net.ServiceApi
import com.ct.paging3.vo.GirlList

/**
 *
 * Paging库的注意事项
 *
 * Paging库默认加载3页数据
 * 比如: 我们一页加载10条数剧 那么PageSize = 10
 *      而在Paging库中会在初始加载的时候默认加载三倍的数据 也就是30条数据 PageSize = 30
 *      这样会导致我们在加载第2，3页的数据的时候 造成数据重复
 * */
class ExamplePagingSource(private val serviceAPi: ServiceApi) : PagingSource<Int, GirlList>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GirlList> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = serviceAPi.getGirlList(nextPageNumber, params.loadSize)

            Log.e(
                "TAG",
                "PagingSource:${params.loadSize} --- ${params.key} --${params.placeholdersEnabled} --数据:${response.data.size}"
            )

            if (response.data.isEmpty())
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            else {
                val newList = response.data.map {
                    GirlList(
                        id = it.id,
                        author = it.author,
                        imgUrl = it.images[0],
                        title = it.title
                    )
                }

                LoadResult.Page(
                    data = newList,
                    prevKey = null,
                    nextKey = if (nextPageNumber == 1) nextPageNumber + 3 else nextPageNumber + 1

                )

            }


        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }

    }
}