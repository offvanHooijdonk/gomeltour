package com.tobe.prediction.domain

/**
 * Created by Yahor_Fralou on 9/17/2018 5:29 PM.
 */

data class UserBean(
        var id: String = "",
        var accountKey: String? = null,
        var name: String = ""
) {
    class Builder(id: String) {
        private var user = UserBean()

        init {
            user.id = id
            user.name = id
        }

        fun build() = user

        fun withName(name: String) {user.also { it.name = name } }

        fun withAccount(accountKey: String) = user.also { it.accountKey = accountKey }

    }
}

fun createUser(id: String, name: String? = null, accountKey: String? = null) =
        UserBean.Builder(id).apply {
            name?.let { this.withName(it) }
            if (accountKey != null) this.withAccount(accountKey)
        }.build()
