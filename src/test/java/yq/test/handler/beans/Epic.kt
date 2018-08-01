package yq.test.handler.beans

data class Epic(
        var name:String = "",
        var description: String = "",
        var features: MutableList<MutableMap<String,Any>>? = null
) {
    val key_name = "name"
    val key_include = "include"
    val key_file = "file"
    val key_dir = "dir"
    val key_exclude = "exclude"
    val key_tags = "tags"
    val key_allIn = "allIn"

}