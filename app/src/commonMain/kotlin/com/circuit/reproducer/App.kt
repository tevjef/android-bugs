package com.circuit.reproducer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import kotlin.random.Random

@Composable
fun App() {
    val screen = remember { CounterScreen(Random.nextInt()) }
    val backStack = rememberSaveableBackStack(root = screen)
    CircuitCompositionLocals(rootAppGraph!!.circuit) {
        val navigator = rememberCircuitNavigator(backStack) { }
        NavigableCircuitContent(
            navigator,
            backStack,
            decoratorFactory = remember(navigator) {
                GestureNavigationDecorationFactory(
                    onBackInvoked = navigator::pop
                )
            },
        )
    }
}

