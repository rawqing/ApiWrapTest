package yq.test.tryTest

import org.junit.Test
import bsh.Interpreter
import yq.test.handler.Parser
import yq.test.handler.Utils.getCaseFiles
import yq.test.handler.Utils.getCases
import yq.test.handler.Utils.readYaml
import yq.test.handler.beans.Case
import yq.test.handler.engine.RunCases


class TryTest {
    @Test
    fun ent(){
        var bsh = Interpreter()
        // Evaluate statements and expressions
        val ms = bsh.eval("new Date()")
        println(ms::class)
        bsh.set("th" ,Parser())
        println (bsh.eval("th._md5(\"123456\")"))
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
    fun tt(){
        val texts = arrayOf("芦花丛中一扁舟", "俊杰俄从此地游", "义士若能知此理", "反躬难逃可无忧")
        val result = texts.map { it.substring(0,1) }.reduce { r, s -> "$r$s"}
        println(result)
//        val case = Case(name = "n1")
//        val list = mutableListOf(case)
//        list.add(list[0].copy())
//        list[1].path = "p1"
//
//        println(list.toString())
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