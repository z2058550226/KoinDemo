package KoinDemo

import org.koin.experimental.property.inject

object Ch7_InjectingAProperty {
    class B
    class C

    class A {
        lateinit var b: B
        lateinit var c: C
    }

    init {
        val a: A = A()
        // inject properties
        a::b.inject()
        a::c.inject()

        // inject all properties
        a.inject(a::b, a::c)
    }
}