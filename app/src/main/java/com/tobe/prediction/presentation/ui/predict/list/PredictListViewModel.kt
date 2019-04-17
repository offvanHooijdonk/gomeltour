package com.tobe.prediction.presentation.ui.predict.list

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tobe.prediction.app.App
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.predict.PredictService
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PredictListViewModel(private val predictService: PredictService) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val predictsList = ObservableArrayList<PredictDTO>()
    val errorMsg = ObservableField<String>()
    val showEmpty = ObservableBoolean(false)

    init {
        loadPredicts()
    }

    fun loadPredicts() {
        predictService.getPredicts()
                .subscribe(
                        { predictsList.apply { clear(); addAll(it); showEmpty.set(it.isEmpty()) } },
                        { th -> Log.e(App.LOGCAT, "Error loading predicts", th); errorMsg.set(th.message) } // todo apply better handling
                ).attachTo(cd)
    }
}