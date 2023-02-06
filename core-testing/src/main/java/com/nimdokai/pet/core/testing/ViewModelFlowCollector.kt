package com.nimdokai.pet.core.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope

@ExperimentalCoroutinesApi
class ViewModelFlowCollector<S, E>(
    private val stateFlow: Flow<S>,
    private val eventFlow: Flow<E>,
) {

    fun runTest(test: suspend TestScope.(List<S>, List<E>) -> Unit): Unit =
        kotlinx.coroutines.test.runTest(TestCoroutineDispatchers.main) {
            val states = mutableListOf<S>()
            val stateJob =
                launch() { stateFlow.toList(states) }
            val events = mutableListOf<E>()
            val eventJob =
                launch() { eventFlow.toList(events) }
            delay(2000)
            test(states, events)
            stateJob.cancel()
            eventJob.cancel()
        }
}