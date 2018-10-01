package com.tobe.prediction.di.presentation.predict

import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictEditDialog
import dagger.Subcomponent

/**
 * Created by Yahor_Fralou on 10/1/2018 12:57 PM.
 */

@Subcomponent(modules = [PredictModule::class])
@PredictScope
interface PredictComponent {
    fun inject(view: PredictEditDialog)

    fun inject(view: PredictListFragment)
}