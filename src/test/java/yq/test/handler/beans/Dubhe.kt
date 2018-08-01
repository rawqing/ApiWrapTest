package yq.test.handler.beans


data class Dubhe(
        var name: String = "Dubhe Tests",
        var describe: MutableMap<String, Any> = HashMap(),
        var listeners: List<String>? = null,
        var epics: MutableList<Any> = ArrayList(),
        var tags: List<String>? = ArrayList()
) {
    var explainedEpicList: MutableList<Epic>? = null
}