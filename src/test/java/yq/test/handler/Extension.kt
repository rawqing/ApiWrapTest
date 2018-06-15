package yq.test.handler

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.internal.RestAssuredResponseImpl
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import yq.test.handler.UtilsJ.SignUtil.sign

/**
 * 给原生的given()加入验证fail输出日志功能
 */
fun given_(): RequestSpecification {
    return RestAssured.given().log().ifValidationFails()
            .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
}

fun RequestSpecification.signedParams(params : Map<String,String>) : RequestSpecification {
    return this.params(sign(params))
}
/**
 * 给原生`when()` 加入验证fail输出日志功能
 */
fun RequestSpecification.when_(): RequestSpecification {
    return this.`when`().log().ifValidationFails()
}

/**
 * 给原生的then() 加入error输出日志功能
 */
fun Response.then_(): ValidatableResponse {
    return this.then().log().all()
}


/**
 * 更改Response的ContentType
 */
fun Response.setContentType(contentType: ContentType): Response {
    var rai = this as RestAssuredResponseImpl
    rai.setContentType(contentType)
    return this
}

/**
 * 默认更改response为 "application/json; charset=utf-8"
 */
fun RequestSpecification.post2json(path: String): Response {
    return this.post(path).setContentType(ContentType.JSON)
}