package com.ct.framework.kt

import androidx.lifecycle.*
import com.ct.framework.jetpack.dto.Category
import kotlinx.coroutines.launch

/**
 * 这里 我们尝试使用协程
 * */
class KtViewModel(private val repository: KtRepository) : ViewModel() {
    fun getGirlList(): LiveData<List<Category>> {
        val result = MutableLiveData<List<Category>>()
        viewModelScope.launch {
            val data = repository.getGirlList()
            result.value = data
        }

        return result
    }

}


class KtVmFactory(private val repository: KtRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KtViewModel(repository) as T
    }
}