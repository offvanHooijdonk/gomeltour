package by.gomeltour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.gomeltour.model.UserModel
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 9/20/2018 12:02 PM.
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserModel)

    @Query("select * from Users where id = :id")
    fun getById(id: String): Maybe<UserModel>

    @Query("select * from Users where id = :id")
    fun getByIdSync(id: String): UserModel
}