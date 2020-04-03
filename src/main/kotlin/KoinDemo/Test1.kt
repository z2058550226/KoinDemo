package KoinDemo

import org.koin.core.context.startKoin

object Test1 {

    init {
        startKoin {
            modules()
        }
    }

}