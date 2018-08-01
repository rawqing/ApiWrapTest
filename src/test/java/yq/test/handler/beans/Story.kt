package yq.test.handler.beans

data class Story(
        var name: String? = null,
        var description: String? = null,
        var data: Any? = null,
        var enabled: Boolean = false,
        var path: String? = null,
        var method: String? = null,
        var headers: Map<String ,Any>? = null,
        var given: Map<String ,Any>? = null,
        var params: MutableMap<String ,Any>? = null,
        var expect: MutableMap<String ,Any>? = null,
        var push: MutableMap<String ,Any>? = null

){
    val filter: StoryFilter? = null
}