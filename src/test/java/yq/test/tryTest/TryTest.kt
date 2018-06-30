package yq.test.tryTest

import org.junit.Test
import bsh.Interpreter
import yq.test.handler.Parser
import yq.test.handler.Utils.getCaseFiles
import yq.test.handler.Utils.getCases
import yq.test.handler.Utils.readYaml
import yq.test.handler.beans.Feature
import yq.test.handler.engine.RunCases
import yq.test.handler.hasShell
import yq.test.handler.mapping.KeyMap.prefix
import yq.test.handler.mapping.KeyMap.suffix
import java.io.File


@Suppress("CAST_NEVER_SUCCEEDS")
class TryTest {
    @Test
    fun ent(){
        val bsh = Interpreter()
        // Evaluate statements and expressions
//        val ms = bsh.eval("new Date()")
        val ree = bsh.eval("\"hello\" + \"world\"")
        println(ree)
        val ps = Parser()

//        println(ps.unscramble("\$_md5(\"123456\")"))




//        val user = bsh.eval("new yq.test.handler.beans.User()")
//        println(user::class.java)
        // 对象获取可用
//        println(ps.unscramble("\${{User()}}"))

//        bsh.eval("bar=foo*5; bar=Math.cos(bar);")
//        bsh.eval("for(i=0; i<10; i++) { print(\"hello\"); }")
//        // same as above using java syntax and apis only
//        bsh.eval("for(int i=0; i<10; i++) { System.out.println(\"hello\"); }")
//
//
//        // Use set() and get() to pass objects in and out of variables
//        bsh.set("date", Date())
//        val date = bsh.get("date") as Date
//        // This would also work:
//        val date1 = bsh.eval("date") as Date
    }
    fun _md5(){
        println("_md5")
    }
    fun _getToken(){

    }

    @Test
    fun entk(){
//        var s = "\$_getToken(user.name,user.password)"
//        s= s.removeRange(0..0)
//        println(s)
        val rc = RunCases()
        for (ca in getCases(getCaseFiles()[0])) {
            rc.runCase(ca)
        }
    }
    @Test
    fun str2fun(){
        val user = mapOf("user" to "\${{User()}}")
        val u =  "{{User()}}"
        var s = "\$_getToken(user.name,user.password)"
        var s1 = "{{_getToken(user.name,\"12300\"),_md5(\"99999\")}}"
        var s2 = "{{_md5(_getToken(user.name,\"12300\"))}}"
        s= s.removeRange(0..0)
        println(s)
        val rs = u.removeSurrounding(prefix, suffix)


    }

    @Test
    fun pTest(){
//        val sh = Interpreter()
        val ps = Parser()
       /* var s1 = "{{$.getToken(user.name,\"12300\",$.md5(\"99999\"));}}"
        var s2 = "{{$.md5($.getToken(user.name,\"12300\"));}}"
        ps.setInstance("user", User(name = "zhangsan"))
        println(ps.sentenceRes(s1))*/

        val map = mapOf("msg" to "123", "code" to 200)
        ps.setInstance("map", map)
        val s3 = "{{map.get(\"msg\")}}"
        println(ps.sentenceRes(s3))
    }

    fun unshell(str: String): String {
        if (str.startsWith(prefix) && str.endsWith(suffix)) {
            return str.removeSurrounding(prefix, suffix)
        }
        return str
    }

    @Test
    fun tt(){
        val rootPath = this::class.java.getResource("/tests/cases/login03.yml").path
        println(rootPath)
        val readYaml = readYaml(File(rootPath), Feature::class.java)
        println(readYaml)
    }

    @Test
    fun caset(){
        val str = "\${\$.md5(\$pwdQ1 + \$aA_1 +\$aA_1)}"
//        val reg = "\\\$[a-z]([a-z]|[A-Z]|[0-9]|_)+".toRegex()
//        println(reg.containsMatchIn(str))
//        val replace = str.replace(reg, "123")
//        println(replace)
        val s = "`/data/\${$.toUp(\$env)}/123/\$path/\${$.toUp(\$env)}/\$user.yml`"
        val ps = Parser()
        val injectVar = ps.explainStringTemplate(s)
        println(injectVar)
    }

    @Test
    fun runTime(){
        val s = "123"
        println(s.hasShell("{","}"))
    }
}