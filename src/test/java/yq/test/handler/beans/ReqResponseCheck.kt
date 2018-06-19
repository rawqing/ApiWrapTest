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
    fun withPath(path: String?): ReqResponseCheck {
        if (path!=null) this.path = path
        return this
    }
    fun withParams(params: MutableMap<String, String>?): ReqResponseCheck {
        if (params!=null)
            this.params = params
        return this
    }

    fun withJsonParams(jsonParams: String?): ReqResponseCheck {
        if (jsonParams!=null)   this.jsonParams = jsonParams
        return this
    }
    fun withHeaders(headers: Headers?): ReqResponseCheck {
        if (headers!=null)  this.headers = headers
        return this
    }
    fun withContentType(contentType: ContentType?): ReqResponseCheck {
        if (contentType!=null)  this.contentType = contentType
        return this
    }
    fun withStatusCode(statusCode: Int): ReqResponseCheck {
        this.statusCode = statusCode
        return this
    }
    fun withBody(body: MutableMap<String, Matcher<*>>?): ReqResponseCheck {
        if (body != null)   this.body = body
        return this
    }
}