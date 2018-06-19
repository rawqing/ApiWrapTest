package yq.test.handler.engine

import org.hamcrest.Matcher
import yq.test.handler.*
import yq.test.handler.Utils.getMaxSize
import yq.test.handler.Utils.log
import yq.test.handler.Utils.polishing
import yq.test.handler.beans.Case
import yq.test.handler.beans.ReqResponseCheck
import yq.test.handler.mapping.KeyMap.bodyKey
import yq.test.handler.mapping.KeyMap.statusCodeKey

class RunCases {

    fun runCase(case: Case): MutableList<Case> {
        val params = case.params
        if (params == null){
            log.info("无参请求")
            return mutableListOf(case)
        }
        // 整个cases 的最大数量以 params 集合的最大数量为准 , 预期结果当小于或等于参数的最大个数
        // so ,这里必须统一 max size
        val maxSize: Int = getMaxSize(params)
        //
        val ps = polishing(params ,maxSize)
        // 封装预期结果
        val es = polishing(case.expect!! ,maxSize)
        val esb = polishing(case.expect!!["body"] as MutableMap<String, Any>,maxSize)
        // 做最后的封箱
        val singleCaseList = ArrayList<Case>()
        for (i in 0 until maxSize) {
            val singleCase = case.copy(params = mutableMapOf() ,expect = mutableMapOf())
            ps.forEach {
                it.forEach{
                    singleCase.params!![it.key] = (it.value)[i]
                }
            }
            es.forEach {
                it.forEach{
                    if (it.key == "body") {
                        val bodyMap = HashMap<String,Any>()
                        esb.forEach {
                            it.forEach {
                                bodyMap[it.key] = (it.value)[i]
                                println("hello")
                            }
                        }
                        singleCase.expect!![it.key]  = bodyMap
                        println("hello")
                    }else {
                        singleCase.expect!![it.key] = (it.value)[i]
                    }
                }
            }
            singleCaseList.add(singleCase)
        }
        return singleCaseList
    }

    fun case2reqResponseCheck(singleCase: Case): ReqResponseCheck {
        var rrc = ReqResponseCheck()
                .withPath(singleCase.path)
                .withParams(singleCase.params?.let { it as MutableMap<String, String> })
                .withStatusCode(singleCase.expect?.let { it[statusCodeKey]?.toString()?.toInt()?:200 }?:200)
                .withBody(singleCase.expect?.let { it[bodyKey] as MutableMap<String, Matcher<*>>})
        return rrc
    }

    fun string2fun(str: String) {

    }
    fun singleCaseRun(req:ReqResponseCheck){
        doPost(req)
    }

    private fun doPost(req:ReqResponseCheck){
        given_()
                .apply {
                    req.headers?.let { this.headers(it) }
                }
                .signedParams(req.params).
        when_()
                .post2json(req.path).
        then_()
                .statusCode(req.statusCode)
                .apply {
                    req.body?.forEach {
                        this.body(it.key ,it.value)
                    }
                }
    }
}