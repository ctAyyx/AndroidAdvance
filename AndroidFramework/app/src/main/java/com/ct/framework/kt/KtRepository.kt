package com.ct.framework.kt

import androidx.annotation.WorkerThread
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.jetpack.net.ServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class KtRepository(private val serviceApi: ServiceApi) {

    @WorkerThread
    suspend fun getGirlList(): List<Category>? {
        return withContext(Dispatchers.IO) {
            delay(5000)
            val call = serviceApi.getGirlList(1, 10)
            val response = call.execute()
            if (response.isSuccessful)
              return@withContext  response.body()?.data
            return@withContext null
        }
    }
}