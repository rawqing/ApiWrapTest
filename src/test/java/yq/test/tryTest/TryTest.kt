package yq.test.tryTest

import org.junit.Test
import bsh.Interpreter
import yq.test.handler.Parser
import yq.test.handler.Utils.getCaseFiles
import yq.test.handler.Utils.getCases
import yq.test.handler.Utils.readYaml
import yq.test.handler.beans.Case
import yq.test.handler.beans.Feature
import yq.test.handler.engine.RunCases
import yq.test.handler.mapping.KeyMap.allPrefix
import yq.test.handler.mapping.KeyMap.funPrefix
import yq.test.handler.mapping.KeyMap.objPrefix
import yq.test.handler.mapping.KeyMap.objSuffix
import yq.test.handler.mapping.KeyMap.paramPrefix
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
        val u = ps.unscramble("\${{User()}}")
        ps.setInstance("user",u)
        val res = ps.unscramble("\${{user.name + user.password}}")
        println(res)

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
        s= s.removeRange(0..0)
        println(s)
        var start =  s.startsWith(funPrefix)
        println(start)
        val rs = u.removeSurrounding(objPrefix, objSuffix)
        println(rs.slice(0..(rs.indexOf(paramPrefix)-1)))


    }


    @Test
    fun tt(){
        val rootPath = this::class.java.getResource("/cases/test/login.yml").path
        println(rootPath)
        val readYaml = readYaml(File(rootPath), Feature::class.java)
        println(readYaml)
    }

    @Test
    fun caset(){
        val rc = RunCases()
        val m = mutableMapOf<String,Any>("p1" to "v1"
                ,"p2" to listOf("1" ,"2")
                ,"p3" to listOf("a")

        )
        rc.runCase(Case(params = m))
    }
}