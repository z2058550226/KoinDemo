package KoinDemo

import org.koin.core.KoinComponent
import org.koin.core.error.NoParameterFoundException
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

object Ch6_InjectingParameters {
    interface View
    class Presenter(val view: View)

    val myModule = module {
        // 这里其实是一个解构声明
        single { (view: View) -> Presenter(view) }
    }

    class MyComponent : View, KoinComponent {
        // 通过这种方式可以给definition进行传值
        val presenter: Presenter by inject { parametersOf(this) }
    }

    // test kotlin grammar
    fun <T> sg(definition: Definition2<T>) {

    }

    init {
        sg { (s: String, i: Int, k0: Boolean, k1: Boolean, k2: Boolean) ->
            "s"
        }
        val definition2: Definition2<String> = { (defPar2: DefinitionParameters2) ->
            "s"
        }
    }
}

class Scope2

typealias Definition2<T> = Scope2.(DefinitionParameters2) -> T

class DefinitionParameters2 internal constructor(vararg val values: Any?) {
    private fun <T> elementAt(i: Int): T =
            if (values.size > i) values[i] as T else throw NoParameterFoundException("Can't get parameter value #$i from $this")

    operator fun <T> component1(): T = elementAt(0)
    operator fun <T> component2(): T = elementAt(1)
    operator fun <T> component3(): T = elementAt(2)
    operator fun <T> component4(): T = elementAt(3)
    operator fun <T> component5(): T = elementAt(4)
}