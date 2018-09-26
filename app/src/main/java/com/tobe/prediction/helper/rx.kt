package com.tobe.prediction.helper

import io.reactivex.Maybe
import io.reactivex.MaybeTransformer
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

fun <T: Any>Maybe<T>.schedulersIO(): Maybe<T> = this.compose(schedulersIOMaybe<T>())


internal val t: ObservableTransformer<Any, Any> = ObservableTransformer { observable ->
    observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

internal val tm: MaybeTransformer<in Any, out Any> = MaybeTransformer { observable ->
    observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> schedulersIO() = t
fun <T> schedulersIOMaybe() = tm as MaybeTransformer<T, T>