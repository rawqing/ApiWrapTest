package yq.test.handler.beans

import io.restassured.http.ContentType
import io.restassured.http.Headers
import org.hamcrest.Matcher

data class ReqResponseCheck(
        var path: String = "/",
        var params: MutableMap<String ,String> = HashMap(),
        var jsonParams: String = "{}",
        var headers: Headers? = null,
        var contentType: ContentType? = null,
        var statusCode: Int = 200,
        var body: MutableMap<String, Matcher<*>>? = null
) {
    fun addParam(key: String, value: String) {

    }
}