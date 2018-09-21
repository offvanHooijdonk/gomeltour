package com.tobe.prediction.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:02 PM.
 */

@Entity
data class Predict(@PrimaryKey(autoGenerate = true) var id: Long?, var text: String, var dateWhen: Date, var isActive: Boolean)