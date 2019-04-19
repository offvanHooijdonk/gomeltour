package com.tobe.prediction.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:02 PM.
 */

@Entity(tableName = "Predicts")
data class Predict(
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        var predictId: String = "",
        var title: String = "",
        var text: String = "",
        var dateOpenTill: Date = Date(),
        var dateFulfillment: Date = Date(),
        var isActive: Boolean = true,
        var userId: String = "",
        var options: Array<String> = arrayOf("", ""))