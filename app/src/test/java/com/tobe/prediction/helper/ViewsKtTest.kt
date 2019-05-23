package com.tobe.prediction.helper

import org.junit.Assert.assertEquals
import org.junit.Test

class ViewsKtTest {
    private val votesConversions = mapOf(
            0 to "0",
            12 to "12",
            345 to "345",
            4567 to "4.5k",
            4056 to "4k",
            45670 to "45.6k",
            123456 to "123k",
            1000000 to "1m"
    )

    @Test
    fun convertVotesPresentation() {
        for ((value, result) in votesConversions) {
            assertEquals(result, convertVotesPresentation(value))
        }
    }
}