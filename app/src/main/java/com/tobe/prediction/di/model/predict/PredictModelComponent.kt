package com.tobe.prediction.di.model.predict

import com.tobe.prediction.di.presentation.predict.PredictModelScope
import dagger.Subcomponent

/**
 * Created by Yahor_Fralou on 10/31/2018 2:11 PM.
 */

@Subcomponent(modules = [PredictModelModule::class])
@PredictModelScope
interface PredictModelComponent {
}