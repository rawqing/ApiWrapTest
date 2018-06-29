package yq.test.handler.hooks

import yq.test.handler.UtilsJ.SignUtil
import yq.test.utils.Armory
import java.util.*

class HookFun {

    fun fun1(): Int {
        return 1+100
    }

    fun md5(string: String): String {
        println(Armory.getDateFormat(Date()))
        return SignUtil.MD5(string)
    }

    fun getToken(name: String, pwd: String ):String {
        val map = mapOf(name to pwd)
        val map1 = map.map { it.value + "V" }
        val s = "$map $map1"
        println(s)
        return s
    }
    fun getToken(name: String, pwd: String ,md5:String ):String {
        return "$name$pwd$md5"
    }
}