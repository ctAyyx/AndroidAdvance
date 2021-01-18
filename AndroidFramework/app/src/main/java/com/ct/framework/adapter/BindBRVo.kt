package com.ct.framework.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ct.framework.BR

data class BindBRVo(

    var car: String
) : BaseObservable() {
    @get:Bindable
    var home: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.home)
        }
}