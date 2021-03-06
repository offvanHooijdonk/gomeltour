package by.gomeltour.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yahor_Fralou on 9/17/2018 5:29 PM.
 */

@Entity(tableName = "Users")
data class UserModel(
        @PrimaryKey var id: String = "",
        var accountKey: String? = null,
        var name: String = "",
        var email: String = "",
        var photoUrl: String? = null
) {
    class Builder(id: String) {
        private var user = UserModel()

        init {
            user.id = id
            user.name = id
        }

        fun build() = user

        fun withName(name: String) = user.apply { this.name = name }

        fun withEmail(email: String) = user.apply { this.email = email }

        fun withAccount(accountKey: String) = user.also { it.accountKey = accountKey }
    }
}

fun createUser(id: String, name: String? = null, email: String? = "", accountKey: String? = null) =
        UserModel.Builder(id).apply {
            name?.let { this.withName(it) }
            email?.let { withEmail(it) }
            if (accountKey != null) this.withAccount(accountKey)
        }.build()
