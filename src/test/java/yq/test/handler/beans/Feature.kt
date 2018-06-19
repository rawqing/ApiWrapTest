package yq.test.handler.beans

class Feature(
        var name: String = "",
        var configSet: Map<String,Any> = HashMap(),
        var case: List<Case> = ArrayList()
) {
}