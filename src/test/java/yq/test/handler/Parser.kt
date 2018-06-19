package yq.test.handler

import yq.test.handler.UtilsJ.SignUtil.MD5
import bsh.Interpreter
import yq.test.handler.mapping.KeyMap
import yq.test.handler.mapping.KeyMap.fragmentPrefix
import yq.test.handler.mapping.KeyMap.fragmentSuffix
import java.util.*

class Parser {

    // 每一个case保持一个单独的实例
    private val sh = Interpreter()
    private val map:Map<String,String> by lazy { Utils.getMapping("/mapping.yml") }

    fun unscramble(str: String):Any{
        if (!str.startsWith(KeyMap.allPrefix))    return str
        var s = str.removeRange(0..0)
        if (s.startsWith(KeyMap.objPrefix) && s.endsWith(KeyMap.objSuffix)) {
        // 这里表示对象注入或对象操作
            //除去前后缀的表达式
            val subStr = s.removeSurrounding(KeyMap.objPrefix, KeyMap.objSuffix)
            //"(" 前的映射名称
            val keyStr = subStr.let { it.slice(0..(it.indexOf(KeyMap.paramPrefix)-1)) }
            // 靠近"("的单词的首字符是否大写
            val firstUpCase = keyStr.split(".")
                    .let { if (it.size >1) it.last() else it.first()}
                    .let { if (it.isEmpty()) false else it.first().isUpperCase() }
            if (firstUpCase) {
                if (map.containsKey(keyStr)) {
                    //若定义了映射则查询映射
                    val value = map[keyStr]
                    return sh.eval("import $value; new $subStr")
                }
                return sh.eval("new $subStr")
            }
            return sh.eval(subStr)
        }
        if (s.startsWith(KeyMap.funPrefix)) {
        // 这里为函数调用
            sh.set("_parser",this)
            return sh.eval("_parser.$s")
        }
        if (s.startsWith(fragmentPrefix) && s.endsWith(fragmentSuffix)) {

        }
        return str
    }

    fun setInstance(str: String, obj: Any) {
        sh.set(str ,obj)

    }


    fun _md5(string: String): String {
        return MD5(string)
    }

    fun _getToken(name: String, pwd: String):String {
        return "$name$pwd"
    }
}