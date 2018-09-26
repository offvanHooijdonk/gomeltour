package com.tobe.prediction.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Yahor_Fralou on 9/17/2018 5:29 PM.
 */

@Entity(tableName = "Users")
data class UserBean(
        @PrimaryKey(autoGenerate = false) var id: String = "",
        @ColumnInfo var accountKey: String? = null,
        @ColumnInfo var name: String = ""
)