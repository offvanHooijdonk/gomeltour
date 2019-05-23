package com.tobe.prediction.domain.dto

import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.UserBean

/**
 * Created by Yahor_Fralou on 10/1/2018 5:34 PM.
 */

data class PredictDTO(
        val id: String,
        val title: String,
        val text: String,
        val options: Array<String>,
        val authorName: String,
        val authorId: String,
        val authorPic: String?,
        var votePosCount: Int = 0,
        var voteNegCount: Int = 0
)

fun convertToPredictDTO(p: Predict, u: UserBean): PredictDTO =
        PredictDTO(
                id = p.predictId,
                title = p.title,
                text = p.text,
                options = p.options,
                authorName = u.name,
                authorId = u.id,
                authorPic = u.photoUrl
        )