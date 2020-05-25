package KoinDemo

import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import org.koin.ext.scope

fun main() {
    startKoin {
        modules(module {
//            factory { EditFragment() }
            scope<EditFragment> {
                scoped {
                    A()
                }
                scoped {
                    B()
                }
            }
        })
    }
//    val editFragment: EditFragment = KoinContextHandler.get().get()
    val editFragment: EditFragment = EditFragment()
    editFragment.apply {
        a = scope.get()
        b = scope.get()
        showC()
    }
}

class A
class B

data class C(val a: A, val b: B)

class EditFragment : KoinComponent {
    lateinit var a: A
    lateinit var b: B

    fun showC() {
        a = scope.get()
        b = scope.get()
        val c: C = C(scope.get(), scope.get())
        println(c.toString())
    }
}