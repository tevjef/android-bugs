package com.circuit.reproducer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlin.random.Random

@Parcelize
data class CounterScreen(val id: Int) : Screen {
    data class CounterState(
        val count: Int,
        val eventSink: (CounterEvent) -> Unit,
    ) : CircuitUiState

    sealed interface CounterEvent : CircuitUiEvent {
        data object Increment : CounterEvent
        data object Decrement : CounterEvent
        data object NextScreen : CounterEvent
    }
}

class CounterPresenter @AssistedInject constructor(
    @Assisted private val screen: CounterScreen,
    @Assisted private val navigator: Navigator,
) : Presenter<CounterScreen.CounterState> {

    @CircuitInject(CounterScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(screen: CounterScreen, navigator: Navigator): CounterPresenter
    }

    @Composable
    override fun present(): CounterScreen.CounterState {
        var count by rememberSaveable {
            mutableStateOf(0)
        }
        println("CounterPresenter: id: ${screen.id} counter: $count")
        return CounterScreen.CounterState(count) { event ->
            when (event) {
                CounterScreen.CounterEvent.Increment -> count++
                CounterScreen.CounterEvent.Decrement -> count--
                CounterScreen.CounterEvent.NextScreen -> {
                    navigator.goTo(CounterScreen(Random.nextInt()))
                }
            }
        }
    }
}

@CircuitInject(CounterScreen::class, AppScope::class)
@Composable
fun CounterUi(state: CounterScreen.CounterState, modifier: Modifier) {
    Box(modifier.fillMaxSize().background(Color.White)) {
        Column(Modifier.align(Alignment.Center)) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = "Count: ${state.count}",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.align(CenterHorizontally),
                onClick = { state.eventSink(CounterScreen.CounterEvent.Increment) }
            ) { Text("Increment") }
            Button(
                modifier = Modifier.align(CenterHorizontally),
                onClick = { state.eventSink(CounterScreen.CounterEvent.Decrement) }
            ) { Text("Decrement") }
            Button(
                modifier = Modifier.align(CenterHorizontally),
                onClick = { state.eventSink(CounterScreen.CounterEvent.NextScreen) }
            ) { Text("Next Screen") }
        }
    }
}

