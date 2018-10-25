package com.tobe.prediction.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Votes")
data class Vote(@PrimaryKey var id: String = "", val user: String, var predict: String, val option: Int, val createDate: Date)

fun createVote(user: String, predict: String, option: Int): Vote =
        Vote(user = user, predict = predict, option = option, createDate = Date())

//private fun composeId(user: String, predict: String) = "$user|$predict" // todo move to DAO?