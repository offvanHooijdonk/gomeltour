package com.tobe.prediction.di.presentation.predict

import android.content.Context
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.model.predict.PredictInteractor
import com.tobe.prediction.presentation.presenter.predict.list.PredictListPresenter
import com.tobe.prediction.presentation.presenter.predict.view.PredictEditPresenter
import com.tobe.prediction.presentation.presenter.predict.view.PredictSinglePresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 10/1/2018 1:54 PM.
 */

@Module
@PredictModelScope
class PredictModule {
    @Provides
    fun provideViewPresenter(predictDao: IPredictDao, voteDao: IVoteDao): PredictEditPresenter = PredictEditPresenter(predictDao, voteDao)

    @Provides
    fun provideListPresenter(/*predictDao: IPredictDao, userDao: IUserDao*/predictInteractor: PredictInteractor): PredictListPresenter = PredictListPresenter(/*predictDao, userDao*/predictInteractor)

    @Provides
    fun provideSinglePresenter(ctx: Context, predictDao: IPredictDao, userDao: IUserDao, voteDao: IVoteDao): PredictSinglePresenter = PredictSinglePresenter(ctx, predictDao, userDao, voteDao)
}
