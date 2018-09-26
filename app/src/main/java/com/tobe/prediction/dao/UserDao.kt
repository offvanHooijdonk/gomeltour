package com.tobe.prediction.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tobe.prediction.domain.UserBean
import io.reactivex.Single

/**
 * Created by Yahor_Fralou on 9/20/2018 12:02 PM.
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserBean): Long

    @Query("SELECT * FROM Users WHERE accountKey = :key")
    fun getByKey(key: String): Single<UserBean>

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getById(id: String): Single<UserBean>
}