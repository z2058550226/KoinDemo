package KoinDemo

import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.koinApplication
import org.koin.dsl.module

object Ch4_StartKoin {
    init {
        // You can’t call the startKoin function more than once. But you can use directly the loadKoinModules() functions.
        val myModule = module { }
        loadKoinModules(myModule)
        unloadKoinModules(myModule)
    }

    // Get a Context for your Koin instance
    object SdkKoinContext {
        var koinApp: KoinApplication? = null
    }

    // 这样Sdk中的koin就不会用到实际项目中的koin context了
    abstract class SdkKoinComponent : KoinComponent {
        // Override default Koin instance, intially target on GlobalContext to yours
        override fun getKoin(): Koin = SdkKoinContext.koinApp!!.koin
    }

    class ACustomKoinComponent : SdkKoinComponent() {
        // inject & get will target MyKoinContext
    }

    fun startSdkKoin() {
        // 如果想在sdk中使用koin，可以将koinApplication隔离开来，防止代码冲突
        // create a KoinApplication
        val myApp = koinApplication {
            // declare used modules
            modules(module {
                // some sdk module
            })
        }
        SdkKoinContext.koinApp = myApp
    }

    fun stopGlobalContext(){
        stopKoin()
    }

    fun stopSdkKoin(){
        SdkKoinContext.koinApp?.close()
    }
}