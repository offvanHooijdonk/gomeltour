package com.tobe.prediction.di.app

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.dao.impl.*
import com.tobe.prediction.di.DM
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Yahor_Fralou on 9/20/2018 12:11 PM.
 */

@Module
class DaoModule {
    @Singleton
    @Provides
    fun provideAppDB(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    @Named(DM.Names.REF_USERS)
    fun provideUserCollection(db: FirebaseFirestore): CollectionReference = db.collection(COLL_USERS)

    @Singleton
    @Provides
    @Named(DM.Names.REF_PREDICTS)
    fun providePredictCollection(db: FirebaseFirestore): CollectionReference = db.collection(COLL_PREDICTS)

    @Singleton
    @Provides
    @Named(DM.Names.REF_VOTES)
    fun provideVoteCollection(db: FirebaseFirestore): CollectionReference = db.collection(COLL_VOTES)

    @Singleton
    @Provides
    fun provideUserDao(@Named(DM.Names.REF_USERS) ref: CollectionReference): IUserDao = UserDao(ref)

    @Singleton
    @Provides
    fun providePredictDao(@Named(DM.Names.REF_PREDICTS) ref: CollectionReference): IPredictDao = PredictDao(ref)

    @Singleton
    @Provides
    fun provideVoteDao(@Named(DM.Names.REF_PREDICTS) ref: CollectionReference): IVoteDao = VoteDao(ref)
}