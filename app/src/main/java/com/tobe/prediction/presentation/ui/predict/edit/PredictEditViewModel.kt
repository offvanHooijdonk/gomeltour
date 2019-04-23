package com.tobe.prediction.presentation.ui.predict.edit

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.Vote
import com.tobe.prediction.helper.Configs
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.Session
import com.tobe.prediction.model.predict.PredictService
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class PredictEditViewModel(private val predictService: PredictService) : BaseViewModel(), KoinComponent {
    override val cd = CompositeDisposable()
    private val configs: Configs by inject()

    val closeCommand = PublishSubject.create<Unit>() // todo close with Observable interface
    val closeConfirmCommand = PublishSubject.create<Unit>()
    private var initialPredict = Predict()

    val showProgress = ObservableBoolean(false)
    val invalidMsg = ObservableField<String>()
    val errorMsg = ObservableField<String>()
    val predict = ObservableField<Predict>(Predict())
    val votePositive = ObservableBoolean()
    val voteNegative = ObservableBoolean()
    val votePositiveValue = ObservableField<String>(configs.optionPosDefault)
    val voteNegativeValue = ObservableField<String>(configs.optionNegDefault)
    val dateFulfillField = ObservableField<Date?>().apply { set(null) }
    val dateOpenTillField = ObservableField<Date?>().apply { set(null) }

    fun setupDialog(initValue: Predict?) {
        if (initValue == null) {
            initialPredict = Predict().apply {
                options[0] = configs.optionPosDefault
                options[1] = configs.optionNegDefault
            }
        } else {
            initialPredict = initValue
        }
        predict.set(initialPredict.copy())
        predict.get()?.apply {
            dateFulfillment = Date(initialPredict.dateFulfillment.time)
            dateOpenTill = Date(initialPredict.dateOpenTill.time)
        }
    }

    fun savePredict() {
        showProgress.set(true)

        val predict = try {
            validate()
        } catch (e: ValidationException) {
            invalidMsg.set(e.message)
            showProgress.set(false)
            return
        }

        val optionId: Int = if (votePositive.get()) configs.optionPosIndex else configs.optionNegIndex

        predict.let {
            setUpId(predict)
            predictService.savePredict(predict, optionId)
                    .doOnError { showProgress.set(false); errorMsg.set(it.message) } // todo implement sync mechanism
                    .subscribe { showProgress.set(false); closeCommand.onNext(Unit) }
                    .attachTo(cd)
        }
    }

    fun cancelEditing() {
        val currentPredict = predict.get()?.apply {
            // todo move this to some method
            fillOptionsValues(this)
            dateFulfillField.get()?.let { dateFulfillment = it }
            dateOpenTillField.get()?.let { dateOpenTill = it }
        }

        if (initialPredict.hashCode() != currentPredict?.hashCode()) {
            closeConfirmCommand.onNext(Unit)
        } else {
            closeCommand.onNext(Unit)
        }
    }

    fun onOptionSelected(isPositive: Boolean, value: Boolean) {
        if (value) {
            if (isPositive) {
                voteNegative.set(false)
            } else {
                votePositive.set(false)
            }
        }
    }

    /**
     * @throws Exception
     */
    private fun validate(): Predict {
        val predict = predict.get()
        if (predict != null) {
            predict.apply {
                text = text.trim().takeIf { it.isNotEmpty() } ?: throw ValidationException("Text cannot be empty")
                title = title.trim().takeIf { it.isNotEmpty() } ?: throw ValidationException("Title cannot be empty")
                dateFulfillment = dateFulfillField.get() ?: throw ValidationException("Date Fulfillment not set")
                dateOpenTill = dateOpenTillField.get() ?: throw ValidationException("Date Fulfillment not set")
                if (!votePositive.get() && !voteNegative.get()) throw ValidationException("Need to pick at least one option")
                options[0] = votePositiveValue.get() ?: throw ValidationException("Please fill the empty option")
                options[1] = voteNegativeValue.get() ?: throw ValidationException("Please fill the empty option")
                predictId = ""
                userId = Session.user!!.id
            }
        } else {
            throw Exception("WTF")
        }

        return predict
    }

    private fun fillOptionsValues(p: Predict) {
        p.apply {
            options[0] = votePositiveValue.get() ?: ""
            options[1] = voteNegativeValue.get() ?: ""
        }
    }

    @Deprecated("Need to set at server")
    private fun setUpId(predict: Predict) {
        predict.predictId = System.currentTimeMillis().toString()
    }

    @Deprecated("Need to set at server")
    private fun setUpId(vote: Vote) = vote.apply { this.id = "${this.predict}+${this.user}" }
}