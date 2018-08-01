package yq.test.handler.listeners

import yq.test.handler.beans.DTestResult
import yq.test.handler.beans.Epic

interface EpicListener :Listener{

    fun beforeEpic(epic: Epic, dTestResult: DTestResult)
    fun afterEpic(epic: Epic ,dTestResult: DTestResult)
}