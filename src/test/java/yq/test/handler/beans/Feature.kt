package yq.test.handler.beans

data class Feature(
        var name: String = "",
        var config: Map<String,Any> = HashMap(),
        var datas: Map<String,Any> = HashMap(),
        var story: List<Story> = ArrayList()
) {
    val storyFilter: StoryFilter? = null
}