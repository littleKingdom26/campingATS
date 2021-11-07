package kr.co.ats.camping.controller.common

import io.swagger.annotations.Api
import kr.co.ats.camping.service.common.CommonService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["common"], description = "공통 컨트롤러")
class CommonRestController {

    private val log = LoggerFactory.getLogger(CommonRestController::class.java)

    @set:Autowired
    lateinit var commonService: CommonService

    /**
     * 공통 이미지 뷰어
     * path : 폴더 경로 / [DB 컬럼 :  FILE_PATH]
     * fileName :  퍼알 이름 / [DB 컬럼 : FILE_NAME]
     */
    @GetMapping(value=["/imageView/{path}/{fileName}"], produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getImageView(
        @PathVariable("path") path: String,
        @PathVariable("fileName") fileName: String
    ) :ByteArray = commonService.getFile(path,fileName).readBytes()

}