package KoinDemo

import org.koin.core.context.GlobalContext
import org.koin.core.context.KoinContextHandler
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ext.getOrCreateScope
import org.koin.ext.scope

/**
 * 一个scope必须要有一个标识符
 */
object Ch8_ScopeAPI {
    private class A
    private class B
    private class C
    private class Presenter
    private class ComponentA
    private class ComponentB(val a: ComponentA)

    private val koin = KoinContextHandler.get()

    init {
        module {
            scope(named("A Scope Name")) {
                scoped { Presenter() }
                // ...
            }
        }

        // 如下声明可以让B和C只存在于A的生命周期
        module {
            factory { A() }
            scope<A> {
                scoped { B() }
                scoped { C() }
            }
        }
        GlobalContext().get()
        // Get A from Koin's main scope
        val a: A = KoinContextHandler.get().get()

        // Get/Create Scope for `a`
        val scopeForA = a.getOrCreateScope()

        // Get scoped instances from `a`
        val b1 = scopeForA.get<B>()
        val c1 = scopeForA.get<C>()

        // Get scoped instances from `a`
        val b2 = a.scope.get<B>()
        val c2 = a.scope.get<C>()

        // Destroy `a` scope & drop `b` & `c`
        a.scope.close()
        // or
        scopeForA.close()

        // More Scope API
        val scope = KoinContextHandler.get().createScope<A>()
        val presenter = scope.get<Presenter>()

        // module with scope
        module {

            scope(named("A_SCOPE_NAME")) {
                scoped { ComponentA() }
                // will resolve from current scope instance
                scoped { ComponentB(get()) }
            }
        }

        // create an closed scope instance "myScope1" for scope "A_SCOPE_NAME"
        val myScope1 = koin.createScope("myScope1", named("A_SCOPE_NAME"))

        // from the same scope
        val componentA = myScope1.get<ComponentA>()
        val componentB = myScope1.get<ComponentB>()
    }
}