package KoinDemo

import org.koin.dsl.module

/**
 * A Koin module is a “space” to gather Koin definition. It’s declared with the `module` function.
 *
 *
 */
object Ch3_Module {
    // ComponentB <- ComponentA
    class ComponentA()

    class ComponentB(val componentA: ComponentA)

    val moduleA = module {
        // Singleton ComponentA
        single { ComponentA() }
    }

    val moduleB = module {
        // Singleton ComponentB with linked instance ComponentA
        single { ComponentB(get()) }
    }

    class Repository(val datasource: Datasource)
    interface Datasource
    class LocalDatasource() : Datasource
    class RemoteDatasource() : Datasource

    val repositoryModule = module {
        single { Repository(get()) }
    }

    val localDatasourceModule = module {
        single<Datasource> { LocalDatasource() }
    }

    val remoteDatasourceModule = module {
        single<Datasource> { RemoteDatasource() }
    }

    // Overriding definition or module
    interface Service

    class ServiceImp : Service
    class TestServiceImp : Service

    val myModuleA = module {

        single<Service> { ServiceImp() }
    }

    val myModuleB = module {

        // override for this definition
        // 使用override可以覆盖已有module
        single<Service>(override = true) { TestServiceImp() }
    }
}