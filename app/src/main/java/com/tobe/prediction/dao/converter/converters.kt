package com.tobe.prediction.dao.converter

import com.google.firebase.firestore.DocumentSnapshot
import com.tobe.prediction.domain.UserBean

/**
 * Created by Yahor_Fralou on 9/26/2018 3:33 PM.
 */

private object UserFields {
    const val ID = "id"
    const val ACCOUNT_KEY = "accountKey"
    const val NAME = "name"
}

fun convertUser(data: DocumentSnapshot): UserBean {
    return with(UserFields) {
        UserBean(
                id = data.getString(ID)!!,
                accountKey = data.getString(ACCOUNT_KEY),
                name = data.getString(NAME)!!
        )
    }
}