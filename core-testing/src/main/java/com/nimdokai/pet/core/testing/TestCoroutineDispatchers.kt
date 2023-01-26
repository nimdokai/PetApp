package com.nimdokai.pet.core.testing

import com.nimdokai.core_util.AppCoroutineDispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher

object TestCoroutineDispatchers : AppCoroutineDispatchers {
    override val main: TestCoroutineDispatcher = TestCoroutineDispatcher()
    override val io: TestCoroutineDispatcher = main
    override val computation: TestCoroutineDispatcher = main
}