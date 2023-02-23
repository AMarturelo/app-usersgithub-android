package com.amarturelo.usersgithub.core

import androidx.databinding.Observable
import androidx.databinding.ObservableField

class CaptureObservableField<T> : Observable.OnPropertyChangedCallback() {
    val capture: ArrayList<T> = arrayListOf()

    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        (sender as? ObservableField<T>)?.get()?.run {
            capture.add(this)
        }
    }
}