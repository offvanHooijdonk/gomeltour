package com.tobe.prediction.dao.impl

import com.google.firebase.firestore.CollectionReference
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.domain.UserBean
import durdinapps.rxfirebase2.RxFirestore
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/26/2018 2:53 PM.
 */

class UserDao @Inject constructor(private var refUsers: CollectionReference) : IUserDao {

    override fun save(user: UserBean) =
            RxFirestore.setDocument(refUsers.document(user.id), user)

    override fun getById(id: String) =
            RxFirestore.getDocument(refUsers.document(id), UserBean::class.java)

}