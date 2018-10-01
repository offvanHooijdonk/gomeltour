package com.tobe.prediction.di.presentation.predict

import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.presentation.presenter.predict.list.PredictListPresenter
import com.tobe.prediction.presentation.presenter.predict.view.PredictEditPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 10/1/2018 1:54 PM.
 */

@Module
@PredictScope
class PredictModule {
    @Provides
    fun provideViewPresenter(dao: IPredictDao): PredictEditPresenter = PredictEditPresenter(dao)

    @Provides
    fun provideListPresenter(predictDao: IPredictDao, userDao: IUserDao): PredictListPresenter = PredictListPresenter(predictDao, userDao)
}
