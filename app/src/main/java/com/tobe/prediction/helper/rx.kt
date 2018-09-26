package com.tobe.prediction.helper

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yahor_Fralou on 9/26/2018 11:29 AM.
 */

fun Disposable.attachTo(cd: CompositeDisposable) {
    cd.add(this)
}

fun <T> schedulersIO(): ObservableTransformer<T, T> = ObservableTransformer { observable ->
    observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
