package yq.test.handler.listeners

import yq.test.handler.beans.DTestResult
import yq.test.handler.beans.Feature

interface FeatureListener:Listener{

    fun beforFeature(feature: Feature, dTestResult: DTestResult)
    fun afterFeature(feature: Feature, dTestResult: DTestResult)
}