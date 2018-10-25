package com.tobe.prediction.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:02 PM.
 */

@Entity(tableName = "Predicts")
data class Predict(
        @PrimaryKey var id: String = "",
        var title: String = "",
        var text: String = "",
        var dateOpenTill: Date = Date(),
        var dateFulfillment: Date = Date(),
        var isActive: Boolean = true,
        var userId: String = "",
        var options: List<String> = listOf())