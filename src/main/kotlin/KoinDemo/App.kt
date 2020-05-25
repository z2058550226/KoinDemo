/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package KoinDemo

import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class App {
    val greeting: String
        get() {
            return "Hello world."
        }
}

fun main(args: Array<String>) {
    println(App().greeting)
    val koin = koinApplication {
        properties(mapOf("one" to "suika"))
    }
    startKoin {

    }
    val koin1 = KoinContextHandler.get()
//    println(koin.getProperty<String>("one"))
}
