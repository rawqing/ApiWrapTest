package yq.test.handler.beans

class Feature(
        var name: String = "",
        var config: Map<String,Any> = HashMap(),
        var datas: Map<String,Any> = HashMap(),
        var case: List<Case> = ArrayList()
) {
}