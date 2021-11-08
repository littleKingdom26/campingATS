package kr.co.ats.camping.controller.common

import io.swagger.annotations.Api
import kr.co.ats.camping.code.Path
import kr.co.ats.camping.dto.common.FileResultDTO
import kr.co.ats.camping.service.common.CommonService
import kr.co.ats.camping.service.notice.NoticeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RestController
@Api(tags = ["common"], description = "공통 컨트롤러")
class CommonRestController {

    private val log = LoggerFactory.getLogger(CommonRestController::class.java)

    @set:Autowired
    lateinit var commonService: CommonService

    @set:Autowired
    lateinit var noticeService: NoticeService

    /**
     * 공통 이미지 뷰어
     * path : 폴더 경로 / [DB 컬럼 :  FILE_PATH]
     * fileName :  퍼알 이름 / [DB 컬럼 : FILE_NAME]
     */
    @GetMapping(value=["/imageView/{path}/{fileName}"], produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getImageView(
        @PathVariable("path") path: String,
        @PathVariable("fileName") fileName: String
    ) :ByteArray = commonService.getFile(Path.valueOf(path).filePath,fileName).readBytes()


    // 파일 다운로드 구축
    /**
     * 공통 파일 다운로드
     * path : 폴더 경로
     */
    @GetMapping(value = ["/fileDownload/{path}/{fileKey}"], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getNoticeFile(@PathVariable fileKey: Long, @PathVariable path:String, @RequestHeader("User-Agent") agent: String): ResponseEntity<Resource> {

        val file: FileResultDTO
        var resource: Resource ?= null
        var originalName = ""
        if (Path.valueOf(path).name.equals(Path.NOTICE.name)) {
            file = noticeService.findByFile(fileKey)
            resource = InputStreamResource(file.file.inputStream())
            originalName = file.oriFileName
        }

        val headers = HttpHeaders()
        originalName = if (agent.contains("Trident")) {
            URLEncoder.encode(originalName, "UTF-8").replace("\\+".toRegex(), " ")
        } else if (agent.contains("Edge")) {
            URLEncoder.encode(originalName, "UTF-8")
        } else {
            String(originalName.toByteArray(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
        }
        headers.add("Content-Disposition", "attachment; fileName=$originalName")
        return ResponseEntity(resource, headers, HttpStatus.OK)
    }

}