package kr.co.ats.camping.controller.notice

import io.swagger.annotations.Api
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.dto.notice.NoticeResultDTO
import kr.co.ats.camping.dto.notice.NoticeSaveDTO
import kr.co.ats.camping.service.notice.NoticeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore


@RestController
@Api(tags = ["Notice API"], description = "공지사항 api 리스트")
@RequestMapping("/api/notice")
class NoticeRestController {

    private val log = LoggerFactory.getLogger(NoticeRestController::class.java)

    @set:Autowired
    lateinit var noticeService: NoticeService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE],consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun noticeSave(@RequestBody noticeSaveDTO: NoticeSaveDTO,@ApiIgnore @AuthenticationPrincipal authUserDTO:AuthUserDTO):ApiResponse{
        log.debug("noticeSaveDTO : $noticeSaveDTO")
        log.debug("authUserDTO : ${authUserDTO.role}")
        val noticeResultDTO = NoticeResultDTO(noticeService.save(noticeSaveDTO))
        log.debug("notice content $noticeResultDTO.content")

        return ApiResponse.ok(noticeResultDTO)
    }

}