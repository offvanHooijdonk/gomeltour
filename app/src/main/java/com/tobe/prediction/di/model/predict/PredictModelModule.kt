package com.tobe.prediction.di.model.predict

import com.tobe.prediction.di.presentation.predict.PredictModelScope
import com.tobe.prediction.model.predict.PredictInteractor
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 10/31/2018 2:13 PM.
 */

@Module
@PredictModelScope
class PredictModelModule {
    @Provides
    fun providePredictModel(): PredictInteractor = PredictInteractor()
}