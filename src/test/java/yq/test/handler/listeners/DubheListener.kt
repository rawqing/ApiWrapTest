package yq.test.handler.listeners

import yq.test.handler.beans.DTestResult
import yq.test.handler.beans.Dubhe

interface DubheListener :Listener{

    fun onStart(dubhe: Dubhe)

    fun onFinish(dubhe: Dubhe ,dTestResult: DTestResult)
}