package yq.test.handler.engine

import yq.test.handler.Utils
import yq.test.handler.Utils.readYaml
import yq.test.handler.beans.Dubhe
import yq.test.handler.beans.Epic
import yq.test.handler.beans.Feature
import yq.test.handler.isYmlFile
import yq.test.handler.listeners.Notification
import java.io.File

class DubheRunner {
    private val casesDir = this::class.java.getResource("/cases").path
    private val notification: Notification = Notification()

    fun runner(){
        val root = getCasesDir()
        // 获取并解析Dubhe
        val dubhe = getDubhe()
        notification.onStart(dubhe)


        println(123)
    }

    /**
     * 获取Epic列表 , 最顶级的存在
     */
    private fun getEpicList(dubhe: Dubhe): List<Epic>{
        val eList = dubhe.epics
        return eList.map {e ->
            val res: ArrayList<Epic> = ArrayList()
            if (e is File) {
                // 解析目录 , 将目录下的每个 yml 文件解析成 Feature list
                if (e.isDirectory) {
                    val dirEpic = Epic(name = e.name ,description = e.name)
                    dirEpic.features = e.listFiles { f ->
                        isYmlFile(f)
                    }?.map {
                        Utils.readYaml(it ,Feature::class.java).toMutableList()
                    }?.reduce { acc, list ->
                        acc.addAll(list)
                        acc
                    }?.map {
                        mutableMapOf<String ,Any>(e.name to it)
                    }?.toMutableList()

                    res.add(dirEpic)
                }
            }else{
                //不为 File 则为 Map (dubhe.yml文件规范其必须为 Map)
                // e 可当作 Epic 实例
                if (e is Map<*,*>){
                    val epic = Epic()
                    e[epic.key_name]?.let { epic.name = it.toString() }
                    e[epic.key_include]?.let {
                        if (it is List<*>) {
                            it.forEach { inc ->
                                // 过滤
                                if (inc is Map<*, *>) {
                                    inc[epic.key_file]?.apply {

                                    }
                                    inc[epic.key_dir]
                                    inc[epic.key_allIn]


                                }else if (inc is String) {
                                    // 字符串可能是file 或 dir
                                }
                            }
                        }
                    }
                    e[epic.key_exclude]?.let {

                    }
                    e[epic.key_allIn]?.let {

                    }
                }
            }
            res
        }.reduce { acc, list ->
            acc.addAll(list)
            acc
        }
//        val root = File(casesDir)
//        if (!root.isDirectory)  throw RuntimeException("根目录设置不正确: ${root.absolutePath}")
//        val ymlFiles = root.listFiles { f -> isYmlFile(f) }
//        val dubheYml = ymlFiles.find { it.name == defaultYml }
//
//        return dubheYml?.let {
//            val list = ArrayList<Epic>()
//            Utils.readYaml(it ,Epic::class.java).forEach { list.add(it) }
//            list
//        }?: ymlFiles.map {
//            val list = ArrayList<Epic>()
//            Utils.readYaml(it ,Epic::class.java).forEach { list.add(it) }
//            list
//        }.reduce { acc, unit ->
//            acc.addAll(unit)
//            acc
//        }
    }

    /**
     * 通过 xxx.yml 文件获取一个 Feature
     */
    private fun getFeature(path: String): Feature? {
        val file = File(path)   // 此处做预留, 后续可能做正则或通配
        return getFeature(file)
    }
    private fun getFeature(file: File): Feature?{
        if (file.exists() && file.isFile && isYmlFile(file)) {
            return readYaml(file ,Feature::class.java).first()
        }
        return null
    }

    /**
     * 通过 目录 获取期内一组 Feature
     */
    private fun getFeatures(path: String , filter :(file: File)->Boolean): ArrayList<Feature>? {
        val file = File(path)   // 此处做预留, 后续可能做正则或通配
        if (file.exists() && file.isDirectory ) {
            val lf = file.listFiles()?.filter {
                filter(file)
            }
            val features = ArrayList<Feature>()
            lf?.forEach {
                getFeature(it)?.apply { features.add(this) }
            }
            return features
        }
        return null
    }

    /**
     * 获取全局的设定
     * 只可存在一个 "dubhe.yml"文件  , 该文件只可有一个dom , 若有多个也只取第一个
     *  即cases 目录下包含若干 suite_xxx.yml 文件的情况
     *  将每一个 suite yml 文件作为一个 epic 类型为 File
     *  否则将 包含的目录作为一个 epic 类型为 File ( 目录仅限于子级目录 )
     *
     *  @return Dubhe
     *          if dubhe.yml 则遵循文件定义 return Epic list
     *          else has dir , 则将 目录 File 当作 Epic 并封装成 list
     */
    private fun getDubhe(): Dubhe {
        val dubheFile = File(this::class.java.getResource("/dubhe.yml").path)
        return if (dubheFile.exists() && dubheFile.isFile) {
            // 存在 dubhe.yml 则使用该配置
            readYaml(dubheFile, Dubhe::class.java).first()
        } else {    // 不存在则需要装配一个默认的
            // 否则则将每个目录当作一个 Epic
            val dubhe = Dubhe(describe = mutableMapOf("description" to "default"))
            val root = getCasesDir()
                // 否则取目录
            val epicDirs =  root.listFiles { f -> f.isDirectory }
            epicDirs?.forEach {
                dubhe.epics.add(it)
            }
            dubhe
        }
    }


    /**
     * 获取root dir , 若不为目录则抛出异常 , 遵循协定优于配置原则
     */
    private fun getCasesDir(): File {
        val root = File(casesDir)
        if (!root.isDirectory)  throw RuntimeException("测试用例根目录设置不正确: ${root.absolutePath}")
        return root
    }
}