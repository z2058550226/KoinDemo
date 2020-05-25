package KoinDemo

import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.core.inject
import org.koin.dsl.module

/**
 * Koin is a DSL to help describe your modules & definitions, a container to make definition resolution.
 * What we need now is an API to retrieve our instances outside of the container. That’s the goal of Koin components.
 *
 * 总体来说，KoinComponent就是一个让你能方便使用get、inject和bind的标记性接口
 */
object Ch5_KoinComponents {

    class MyService

    fun main(args: Array<String>) {
        val myModule = module {
            // Define a singleton for MyService
            single { MyService() }
        }
        startKoin {
            modules(myModule)
        }

        // Create MyComponent instance and inject from Koin container
        MyComponent()
    }

    class MyComponent : KoinComponent {

        // lazy inject Koin instance
        val myService : MyService by inject()

        // or
        // eager inject Koin instance
        val myService2 : MyService = get()
    }

}