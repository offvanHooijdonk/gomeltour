package com.tobe.prediction.di.app

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.impl.UserDao
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
    companion object {
        private const val COLL_USERS = "users"
    }

    @Singleton
    @Provides
    fun provideAppDB(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    @Named(DM.Names.REF_USERS)
    fun provideUserCollection(db: FirebaseFirestore): CollectionReference = db.collection(COLL_USERS)

    @Singleton
    @Provides
    fun provideUserDao(): IUserDao = UserDao()
}