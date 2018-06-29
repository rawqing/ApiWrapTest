package yq.test.handler

import bsh.Interpreter
import yq.test.handler.hooks.HookFun
import yq.test.handler.mapping.KeyMap
import yq.test.handler.mapping.KeyMap.bshObj
import yq.test.handler.mapping.KeyMap.fragmentPrefix
import yq.test.handler.mapping.KeyMap.fragmentSuffix
import yq.test.handler.mapping.KeyMap.prefix
import yq.test.handler.mapping.KeyMap.suffix

@Suppress("UNUSED_EXPRESSION")
class Parser {

    // 每一个case保持一个单独的实例
    private val sh = Interpreter()
    private val map: Map<String, String> by lazy { Utils.getMapping("/mapping.yml") }
    private val infuse: Map<String, String> = HashMap()


    fun unscramble(str: String):Any{
        if (!str.startsWith(KeyMap.bshObj))    return str
        var s = str.removeRange(0..0)
        if (s.startsWith(KeyMap.prefix) && s.endsWith(KeyMap.suffix)) {
        // 这里表示对象注入或对象操作
            //除去前后缀的表达式
            val subStr = s.removeSurrounding(KeyMap.prefix, KeyMap.suffix)
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

    /**
     * 为语句注入定义的钩子变量 $abc $a001 等
     */
    fun injectVar(str: String): String{
        val reg = Regex(pattern = "\\\$[a-z]([a-z]|[A-Z]|[0-9]|_)+")
        val matches = reg.containsMatchIn(str)
        if (matches) {
            reg.findAll(str)
                    .map { it.value }
                    .distinct() // 去重
                    .forEach {
                        // 将参数的值注入给执行器
                        setInstance(it ,pullVar(it))
                    }
        }
        return str
    }

    private fun pullVar(str: String): Any {

        return str.slice(0..str.length-2)
    }

    /**
     * 执行一条语句 , 并获得结果
     */
    fun sentenceRes(str: String): Any? {
        if (!str.hasShell(prefix, suffix))
            return str
        val sub = str.removeSurrounding(prefix, suffix)
        if (sub.contains(bshObj))
            sh.set(bshObj ,HookFun())
        return sh.eval(sub)
    }

    /**
     * set 一个变量进去
     */
    fun setInstance(str: String, obj: Any) {
        sh.set(str ,obj)
    }

}