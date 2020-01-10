package by.gomeltour.helper

import org.junit.Assert.assertEquals
import org.junit.Test

class ViewsKtTest {
    private val votesConversions = mapOf(
            0 to ("0" to 0),
            12 to ("12" to 0),
            345 to ("345" to 0),
            4567 to ("4.5" to 1),
            4056 to ("4" to 1),
            45670 to ("45.6" to 1),
            123456 to ("123" to 1),
            1000000 to ("1" to 2)
    )

    @Test
    fun convertVotesPresentation() {
        for ((value, result) in votesConversions) {
            assertEquals(result, convertVotesPresentation(value))
        }
    }

    /*@Test
    fun test_initBlocks() {
        class Sample(val x: Int = 5) {
            constructor() : this(10) {
                println("Constructor 1.0 x = $x, y = $y, z = $z")
                y = 12
            }

            var y = 1

            init {
                println("Init 1.0 x = $x, y = $y")
                y = 6
            }

            val z = 12

            init {
                println("Init 2.0 x = $x, y = $y, z = $z")
                y = 21
            }
        }

        val s1 = Sample()
        val s2 = Sample(6)

        println("Sample 1 : ${s1.y}")
        println("Sample 2 : ${s2.y}")
    }*/

    /*@Test
    fun test_Flows() {
        val ints: Flow<Int> = flow {
            for (i in 1..10) {
                delay(100)
                emit(i * 2)
            }
        }

        fun <T> Flow<T>.buffer(size: Int = 0): Flow<T> = flow {
            coroutineScope {
                val channel = this.produce(capacity = size) {
                    this@buffer.collect {
                        send(it)
                    }
                }
                channel.consumeEach {
                    this@flow.emit(it)
                }
            }
        }

        launchScopeIO {
            val time = measureTimeMillis {
                ints.buffer().collect {
                    delay(100)
                    println("$it,")
                }
            }
            println(" Collected in $time ms.")
        }
    }*/
}
