package com.tobe.prediction.presentation.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected abstract val cd: CompositeDisposable

    override fun onCleared() {
        super.onCleared()

        cd.dispose()
    }
}