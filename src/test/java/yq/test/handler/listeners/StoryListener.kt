package yq.test.handler.listeners

import yq.test.handler.beans.DTestResult
import yq.test.handler.beans.Story

interface StoryListener:Listener {

    fun beforeStory(story: Story ,dTestResult: DTestResult)
    fun afterStory(story: Story ,dTestResult: DTestResult)
    fun onStorySuccess(story: Story ,dTestResult: DTestResult)
    fun onStoryFailure(story: Story ,dTestResult: DTestResult)
    fun onStorySkipped(story: Story ,dTestResult: DTestResult)
}