package com.tobe.prediction.di.presentation.predict

import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.presentation.presenter.predict.list.PredictListPresenter
import com.tobe.prediction.presentation.presenter.predict.view.PredictEditPresenter
import com.tobe.prediction.presentation.presenter.predict.view.PredictSinglePresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 10/1/2018 1:54 PM.
 */

@Module
@PredictScope
class PredictModule {
    @Provides
    fun provideViewPresenter(predictDao: IPredictDao, voteDao: IVoteDao): PredictEditPresenter = PredictEditPresenter(predictDao, voteDao)

    @Provides
    fun provideListPresenter(predictDao: IPredictDao, userDao: IUserDao): PredictListPresenter = PredictListPresenter(predictDao, userDao)

    @Provides
    fun provideSinglePresenter(predictDao: IPredictDao, userDao: IUserDao): PredictSinglePresenter = PredictSinglePresenter(predictDao, userDao)
}
