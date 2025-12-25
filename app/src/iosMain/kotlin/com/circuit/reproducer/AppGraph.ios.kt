package com.circuit.reproducer

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph

fun initIosGraph() {
    val iosGraph = createGraph<IOSAppGraph>()
    rootAppGraph = iosGraph
}

@DependencyGraph(AppScope::class)
interface IOSAppGraph : AppGraph

