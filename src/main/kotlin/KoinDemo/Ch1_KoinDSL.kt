package KoinDemo

import KoinDemo.beans.ABean
import KoinDemo.beans.Activity
import KoinDemo.beans.BBean
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Ch1_KoinDSL {

    init {
        // start a KoinApplication in Global context
        startKoin {
            // koin logger
            logger(EmptyLogger())
            // 全局module
            modules(module {
                // 每次依赖这个module都会重新初始化
                factory {
                    BBean()
                }
                // 每次依赖这个module都重用之前的初始化
                single {
                    // 依赖bBean
                    val bBean = get<BBean>()
                    bind<BBean, Int> { parametersOf(1, 2, 3) }
                    ABean()
                }
                // define a logical group for `scoped` definition
                scope<Activity> {
                    Activity()
                    scoped {

                    }
                }
                // named可以用枚举、字符串或类型来标示一个module
                factory(named("suika")) { ABean() }
            }, module { })
            // 全局键值对
            properties(mapOf("one" to 1, "name" to "suika"))
            // 从指定文件中读全局键值对
            fileProperties("/suika_koin.properties")
            // 从系统环境变量中读全局键值对
            environmentProperties()
        }
    }

}