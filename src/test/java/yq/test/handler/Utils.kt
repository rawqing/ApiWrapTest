package yq.test.handler

import com.esotericsoftware.yamlbeans.YamlException
import com.esotericsoftware.yamlbeans.YamlReader
import org.slf4j.LoggerFactory
import yq.test.handler.beans.Story
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader

object Utils{
    var ENV: Environment = Environment.TEST
    val log = LoggerFactory.getLogger("yq_default")

    /**
     * 从文件中读取所有的yaml doc 并转成bean
     */
    fun <T> readYaml(file: File, clazz: Class<T>): List<T>{
        var reader: YamlReader? = null
        try {
            reader = YamlReader(FileReader(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val cases = ArrayList<T>()
        while (true) {
            var contact: T? = null
            try {
                contact = reader!!.read(clazz)
            } catch (e: YamlException) {
                e.printStackTrace()
            }
            if (contact == null) break
            cases.add(contact)
        }
        return cases
    }
    fun getCases(file: File): List<Story>{
        return readYaml(file ,Story::class.java)
    }

    /**
     * 从cases 目录下相应环境中读取所有的 yml 文件
     */
    fun getCaseFiles(): List<File>{
        val rootPath = this::class.java.getResource("/cases/${ENV.name.toLowerCase()}").path
        val file = File(rootPath)
        if (file.isDirectory) {
            return file.listFiles().toList()
        }
        return ArrayList()
    }

    /**
     * 获取一个map映射
     */
    fun getMapping(file: File): MutableMap<String, String> {
        var reader: YamlReader? = null
        try {
            reader = YamlReader(FileReader(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val map = HashMap<String,String>()
        while (true) {
            var contact: Map<*, *>? = null
            try {
                contact = reader!!.read(Map::class.java)
            } catch (e: YamlException) {
                e.printStackTrace()
            }
            if (contact == null) break
            map.putAll(contact as Map<out String, String>)
        }
        return map
    }
    fun getMapping(filePath: String): MutableMap<String, String> {
        val rootPath = this::class.java.getResource(filePath).path
        return getMapping(File(rootPath))
    }

    /**
     * 按指定的最大容量补齐 , 已最大容量为所有容量
     */
    fun polishing(node: MutableMap<String ,Any>, max: Int): List<Map<String, MutableList<Any>>> {
        return node.map{
            val value = it.value
            if (value is List<*>){
                val tmp: MutableList<Any> = value.toMutableList() as MutableList<Any>
                if (tmp.size < max) {
                    tmp.add(tmp.last())
                }
                mapOf(it.key to tmp)
            }else   mapOf(it.key to mutableListOf(value).apply { for (i in 2 ..max){ add(value)} })
        }
    }

    fun polishing(node: MutableMap<String ,Any>): List<Map<String, MutableList<Any>>> {
        return polishing(node , getMaxSize(node))
    }

    /**
     * 获取 map 中最大的那个 List 的容量 , 若不包含list则return 1
     */
    fun getMaxSize(target: MutableMap<String ,Any>):Int{
        return target.map{
            val value = it.value
            if (value is List<*>){
                value.size
            }else
                1
        }.max()?: 1
    }

    fun getFixedClass(className: String): Class<*>? {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }
}

enum class Environment{
    OL,PRE,TEST
}