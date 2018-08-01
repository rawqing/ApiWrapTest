package yq.test.handler.listeners

import yq.test.handler.Utils
import yq.test.handler.beans.*

class Notification(
        var epicListeners: MutableList<EpicListener> = ArrayList(),
        var featureListeners: MutableList<FeatureListener> = ArrayList(),
        var storyListeners: MutableList<StoryListener> = ArrayList(),
        var dubheListeners: MutableList<DubheListener> = ArrayList()
) :DubheListener,EpicListener ,FeatureListener ,StoryListener{


    override fun onStart(dubhe: Dubhe) {
        addListeners(dubhe.listeners)
        dubheListeners.forEach { it.onStart(dubhe) }
    }

    override fun onFinish(dubhe: Dubhe, dTestResult: DTestResult) {
        dubheListeners.forEach { it.onFinish(dubhe,dTestResult) }
    }

    override fun beforeEpic(epic: Epic, dTestResult: DTestResult) {
        epicListeners.forEach { it.beforeEpic(epic ,dTestResult) }
    }

    override fun afterEpic(epic: Epic, dTestResult: DTestResult) {
    }

    override fun beforFeature(feature: Feature, dTestResult: DTestResult) {
    }

    override fun afterFeature(feature: Feature, dTestResult: DTestResult) {
    }

    override fun beforeStory(story: Story, dTestResult: DTestResult) {
    }

    override fun afterStory(story: Story, dTestResult: DTestResult) {
    }

    override fun onStorySuccess(story: Story, dTestResult: DTestResult) {
    }

    override fun onStoryFailure(story: Story, dTestResult: DTestResult) {
    }

    override fun onStorySkipped(story: Story, dTestResult: DTestResult) {
    }


    private fun addListener(listener: Listener) {
        when (listener) {
            is DubheListener -> this.dubheListeners.add(listener)
            is EpicListener -> this.epicListeners.add(listener)
            is FeatureListener -> this.featureListeners.add(listener)
            is StoryListener -> this.storyListeners.add(listener)
        }
    }
    private fun addListeners(listeners: List<String>?){
        listeners?.mapNotNull {
            Utils.getFixedClass(it)
        }?.forEach {
            val inst = it.newInstance()
            if (inst is Listener)
            addListener(inst)
        }
    }


}