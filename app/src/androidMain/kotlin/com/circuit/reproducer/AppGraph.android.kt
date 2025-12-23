package com.circuit.reproducer

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph

fun initAndroidGraph(): AppGraph {
    rootAppGraph = createGraph<AndroidAppGraphAppGraph>()
    return rootAppGraph!!
}

@DependencyGraph(AppScope::class)
interface AndroidAppGraphAppGraph : AppGraph

