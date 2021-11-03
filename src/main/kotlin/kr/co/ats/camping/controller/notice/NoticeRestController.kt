package kr.co.ats.camping.controller.notice

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.notice.NoticeResultDTO
import kr.co.ats.camping.dto.notice.NoticeSaveDTO
import kr.co.ats.camping.dto.notice.NoticeSearchDTO
import kr.co.ats.camping.dto.notice.NoticeUpdateDTO
import kr.co.ats.camping.service.notice.NoticeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@Api(tags = ["Notice API"], description = "공지사항 api 리스트")
@RequestMapping("/api/notice")
class NoticeRestController {

    private val log = LoggerFactory.getLogger(NoticeRestController::class.java)

    @set:Autowired
    lateinit var noticeService: NoticeService

    @Autowired
    lateinit var messageSource: MessageSource


    /**
     * 공지사항 저장
     */
    @ApiOperation(value = "공지사항 등록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE],consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun noticeSave(noticeSaveDTO: NoticeSaveDTO):ApiResponse{
        log.info("NoticeRestController.noticeSave")
        val notice = noticeService.save(noticeSaveDTO)
        return ApiResponse.ok(NoticeResultDTO(notice))
    }

    @ApiOperation(value = "공지사항 상세 정보 ", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/detail/{noticeKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun noticeDetail(@ApiParam(value = "공지사항 키", name = "noticeKey", example = "66") @PathVariable noticeKey: Long): ApiResponse = ApiResponse.ok(NoticeResultDTO(noticeService.findById(noticeKey)))


    @ApiOperation(value = "공지사항 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun noticePageList(noticeSearchDTO: NoticeSearchDTO):ApiResponse{
        log.info("NoticeRestController.noticePageList")
        return ApiResponse.ok(noticeService.findByPage(noticeSearchDTO))
    }

    @ApiOperation(value = "공지사항 수정", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PutMapping(value = ["/{noticeKey}"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun noticeUpdate(@ApiParam(value = "공지사항 키", name = "noticeKey", example = "66") @PathVariable noticeKey: Long, @RequestBody noticeUpdateDTO: NoticeUpdateDTO): ApiResponse =
        ApiResponse.ok(NoticeResultDTO(noticeService.noticeUpdate(noticeKey, noticeUpdateDTO)))

    @ApiOperation(value = "공지사항 삭제", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @DeleteMapping(value = ["/{noticeKey}"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun noticeDelete(@ApiParam(value = "공지사항 키", name = "noticeKey", example = "66") @PathVariable noticeKey: Long): ApiResponse {
        log.info("NoticeRestController.noticeDelete")
        noticeService.noticeDelete(noticeKey)
        return ApiResponse.okMessage(message = messageSource.getMessage("MESSAGE.DELETE",null, Locale.getDefault()))
    }

    // 파일 추가

    // 파일 삭제

}