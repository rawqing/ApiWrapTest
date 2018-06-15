package yq.test.handler.beans

data class Case(
        var name: String? = null,
        var description: String? = null,
        var path: String? = null,
        var method: String? = null,
        var headers: Map<String ,Any>? = null,
        var given: Map<String ,Any>? = null,
        var params: MutableMap<String ,Any>? = null,
        var expect: MutableMap<String ,Any>? = null
){

}