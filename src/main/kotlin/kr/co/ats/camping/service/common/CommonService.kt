package kr.co.ats.camping.service.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream

@Service
class CommonService {

    @Value("\${file.upload.path}")
    lateinit var root: String

    fun getFile(path: String, fileName: String) : FileInputStream {
        return File(root + File.separator + path + File.separator + fileName).inputStream()

    }
}