package kr.co.ats.camping.controller.notice

import io.swagger.annotations.Api
import kr.co.ats.camping.service.notice.NoticeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@Api(tags = ["Notice API"], description = "공지사항 파일 다운로드")
@RequestMapping("/api/notice")
class NoticeController {

    private val log = LoggerFactory.getLogger(NoticeController::class.java)

    @set:Autowired
    lateinit var noticeService: NoticeService

    // 파일 다운로드 구축

}