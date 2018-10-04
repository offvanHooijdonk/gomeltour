package com.tobe.prediction.domain

import java.util.*

data class Vote(var id: String = "", val user: String, var predict: String, val option: Int, val createDate: Date)

fun createVote(user: String, predict: String, option: Int): Vote =
        Vote(user = user, predict = predict, option = option, createDate = Date())

//private fun composeId(user: String, predict: String) = "$user|$predict" // todo move to DAO?