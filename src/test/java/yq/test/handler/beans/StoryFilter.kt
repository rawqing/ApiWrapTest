package yq.test.handler.beans

interface StoryFilter {

    fun filter(story: Story): Boolean
}