package com.tobe.prediction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobe.prediction.domain.UserBean
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 9/20/2018 12:02 PM.
 */

@Dao
interface IUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserBean)

    @Query("select * from Users where id = :id")
    fun getById(id: String): Maybe<UserBean>
}