package KoinDemo

import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

/**
 * koin中称single, factory and scoped这种module内的定义方法为Definition。
 * single, factory and scoped 关键字都通过一个lambda定义，
 * 这个lambda描述了你创建这个组件的逻辑，通常是new一个对象或者一些构建器之类的代码。
 */
object Ch2_Definitions {
    class MyService
    class Controller()

    init {
        val myModule = module {
            // 创建一个MyService单例
            single { MyService() }

            // A factory component declaration is a definition that will
            // gives you a new instance each time you ask for this definition
            // (this instance is not retrained by Koin container, as it won’t inject this instance in other definitions later).
            factory { Controller() }

        }
    }

    // Service interface
    interface Service {

        fun doSomething()
    }

    // Service Implementation
    class ServiceImp : Service {

        override fun doSomething() {
            //...
        }
    }

    class ServiceImp1 : Service {
        override fun doSomething() {
        }
    }

    class ServiceImp2 : Service {
        override fun doSomething() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    val bindingInterfaceModule = module {

        // Will match type ServiceImp only
        single { ServiceImp() }

        // Will match type Service only
        single { ServiceImp() as Service }

        // You can also use the inferred type expression:
        // Will match type Service only
        single<Service> { ServiceImp() }

        // 一些情况下需要多个标识符绑定同一个definition
        // Will match types ServiceImp & Service
        single { ServiceImp() } bind Service::class

        // 为了区分相同类型的两个不同的definition，可以使用named
        single<Service>(named("default")) { ServiceImp() }
        single<Service>(named("test")) { ServiceImp() }

        // 如果同时使用类型和named标识，那么get时会优先找通过类型找，然后如果get特地指定了named才会找named标识的Service
        single<Service> { ServiceImp1() }
        single<Service>(named("test")) { ServiceImp2() }

        // eager creation for this definition
        single<Service>(createdAtStart = true) { ServiceImp() }

        // startKoin会识别createAtStart标识
        startKoin {

        }

        // Note: 如果想要在一个特定的时机触发definition的初始化（比如在工作线程），那么就可以在定义那个线程执行相应的get/inject一遍就可以了

        // koin不会识别如下定义的范型区别，而是会理解为后定义的覆盖先定义的
        single { ArrayList<Int>() }
        single { ArrayList<String>() }

        // 如果想要定义这样的两个list，可以这样：
        single(named("Ints")) { ArrayList<Int>() }
        single(named("Strings")) { ArrayList<String>() }
    }

    val eagerModule = module(createdAtStart = true) {

    }

    val service: Service by inject(Service::class.java, qualifier = named("default"))
}