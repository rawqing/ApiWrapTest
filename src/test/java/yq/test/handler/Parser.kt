package yq.test.handler

import bsh.Interpreter
import yq.test.handler.constant.sentenceRegex
import yq.test.handler.constant.varRegex
import yq.test.handler.hooks.HookFun
import yq.test.handler.mapping.KeyMap.bshObj
import yq.test.handler.mapping.KeyMap.prefix
import yq.test.handler.mapping.KeyMap.suffix
import yq.test.handler.mapping.KeyMap.template_

@Suppress("UNUSED_EXPRESSION")
class Parser {

    // 每一个case保持一个单独的实例
    private val sh = Interpreter()
    private val map: Map<String, String> by lazy { Utils.getMapping("/mapping.yml") }
    private val infuse: Map<String, String> = HashMap()
    private var testI = 0;


/*    fun unscramble(str: String):Any{
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
    }*/

    /**
     * 为语句注入定义的钩子变量 $abc $a001 等
     * 仅限于语句中的变量注入
     */
    private fun injectVar(str: String): String{
        val matches = varRegex.containsMatchIn(str)
        if (matches) {
            varRegex.findAll(str)
                    .map { it.value }
                    .distinct() // 去重
                    .forEach {
                        // 将参数的值注入给执行器
                        setInstance(it ,pullVar(it))
                    }
        }
        return str
    }

    /**
     * 拉取一个变量的值 , 根据变量名称
     * 根据当前的变量的作用域
     */
    private fun pullVar(str: String): Any {

        return str.slice(1..str.length-2)
    }

    /**
     * 执行一条语句 , 并获得结果
     */
    fun sentenceRes(str: String): Any? {
        if (!str.hasShell(prefix, suffix))
            return str
        var sub = str.removeSurrounding(prefix, suffix)
        // 为语句注入变量 , 如果包含变量的话
        sub = injectVar(sub)
        if (sub.contains(bshObj))
            sh.set(bshObj ,HookFun())
        return sh.eval(sub)
    }

    /**
     * 字符串模板解释
     * 可解释的样例:  /test/${$.get($env)}/story/${$.get($filename)}/$user.yml
     * @param str 非壳后的字符串
     */
    fun explainStringTemplate(str: String): String{
        if (! str.hasShell(template_ ,template_))
            return str
        return str.
                removeSurrounding(template_,template_).
                let {
                    // 解释里面的句子
                    it.replaceAll(sentenceRegex ,{ sentenceRes(it).toString()})
                }.
                let {
                    // 解释里面的变量
                    it.replaceAll(varRegex ,{ pullVar(it).toString()})
                }
    }

    fun testfun(): String {
        testI += 1
        return "$testI"
    }
    /**
     * set 一个变量进去
     */
    fun setInstance(str: String, obj: Any) {
        sh.set(str ,obj)
    }

}