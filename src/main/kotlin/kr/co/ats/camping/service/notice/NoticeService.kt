package kr.co.ats.camping.service.notice

import kr.co.ats.camping.dto.notice.NoticeSaveDTO
import kr.co.ats.camping.entity.notice.Notice
import kr.co.ats.camping.repository.notice.NoticeRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NoticeService {
    private val log = LoggerFactory.getLogger(NoticeService::class.java)


    @set:Autowired
    lateinit var noticeRepository: NoticeRepository

    fun save(noticeSaveDTO: NoticeSaveDTO):Notice {

        return noticeRepository.save(Notice(noticeSaveDTO.subject, noticeSaveDTO.content))

    }
}