package yq.test.handler

import yq.test.handler.UtilsJ.SignUtil.MD5

class Parser {

    fun _md5(string: String): String {
        return MD5(string)
    }
}