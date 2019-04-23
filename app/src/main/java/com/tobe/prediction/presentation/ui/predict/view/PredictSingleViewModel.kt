package com.tobe.prediction.presentation.ui.predict.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.predict.PredictService
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PredictSingleViewModel(private val predictService: PredictService) : BaseViewModel() {
    override val cd = CompositeDisposable()

    private var predictId: String? = null

    val errorMsg = ObservableField<String>()
    val predict = ObservableField<PredictDTO>()
    val progressLoad = ObservableBoolean(false)

    fun setPredictId(id: String) {
        if (predictId == null) {
            predictId = id
            loadPredictData(id)
        }
    }

    private fun loadPredictData(id: String) {
        progressLoad.set(true)
        predictService.loadPredictData(id)
                .subscribe({ dto ->
                    predict.set(dto)
                    progressLoad.set(false)
                }, { th ->
                    errorMsg.set(th.message)
                    progressLoad.set(false)
                }).attachTo(cd)

        predict.get()
    }
}