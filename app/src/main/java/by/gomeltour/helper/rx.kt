package by.gomeltour.helper

import io.reactivex.*
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

fun <T : Any> Maybe<T>.schedulersIO(): Maybe<T> = this.compose(schedulersIOMaybe<T>())
fun <T : Any> Observable<T>.schedulersIO(): Observable<T> = this.compose(schedulersIOObservable<T>())
fun Completable.schedulersIO(): Completable = this.compose(schedulersIOCompletable())


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

internal val tc: CompletableTransformer = CompletableTransformer { observable ->
    observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> schedulersIOObservable() = t as ObservableTransformer<T, T>
fun <T> schedulersIOMaybe() = tm as MaybeTransformer<T, T>
fun schedulersIOCompletable() = tc