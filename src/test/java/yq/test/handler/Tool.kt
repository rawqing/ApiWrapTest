package yq.test.handler

import yq.test.handler.constant.caseExtension
import java.io.File

fun isYmlFile(file: File): Boolean {
    return file.extension == caseExtension
}