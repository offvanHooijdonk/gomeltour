package com.tobe.prediction.dao.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.converter.convertUser
import com.tobe.prediction.domain.UserBean
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 9/26/2018 2:53 PM.
 */

class UserDao /*@Inject*/ constructor() : IUserDao {
    /*@Inject
    @Named(DM.Names.REF_USERS)*/
    /*lateinit*/ var refUsers: CollectionReference = FirebaseFirestore.getInstance().collection("users")

    override fun save(user: UserBean) =
        RxFirestore.setDocument(refUsers.document(user.id), user)

    override fun getById(id: String): Maybe<UserBean> {
        return RxFirestore.getDocument(refUsers.document(id))
                .map { data ->  convertUser(data) }
    }
}