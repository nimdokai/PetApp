@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nimdokai.pet.core.testing

import com.nimdokai.core_util.AppCoroutineDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

object TestCoroutineDispatchers : AppCoroutineDispatchers {
    override val main: TestDispatcher = StandardTestDispatcher()
    override val io: TestDispatcher = main
    override val computation: TestDispatcher = main
}