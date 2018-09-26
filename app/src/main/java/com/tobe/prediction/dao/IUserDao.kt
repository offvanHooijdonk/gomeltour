package com.tobe.prediction.dao

import com.tobe.prediction.domain.UserBean
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 9/20/2018 12:02 PM.
 */

interface IUserDao {
    fun save(user: UserBean): Completable

    fun getById(id: String): Maybe<UserBean>
}