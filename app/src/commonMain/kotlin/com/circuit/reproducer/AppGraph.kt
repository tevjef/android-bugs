package com.circuit.reproducer

import com.slack.circuit.foundation.Circuit

interface AppGraph {
    val circuit: Circuit
}

var rootAppGraph: AppGraph? = null

