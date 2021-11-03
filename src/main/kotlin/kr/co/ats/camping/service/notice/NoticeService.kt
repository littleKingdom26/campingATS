package kr.co.ats.camping.service.notice

import kr.co.ats.camping.config.exception.CampingATSException
import kr.co.ats.camping.dto.common.FileDTO
import kr.co.ats.camping.dto.notice.NoticePageResultDTO
import kr.co.ats.camping.dto.notice.NoticeSaveDTO
import kr.co.ats.camping.dto.notice.NoticeSearchDTO
import kr.co.ats.camping.dto.notice.NoticeUpdateDTO
import kr.co.ats.camping.entity.notice.Notice
import kr.co.ats.camping.entity.notice.NoticeFile
import kr.co.ats.camping.repository.notice.NoticeRepository
import kr.co.ats.camping.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class NoticeService {
    private val log = LoggerFactory.getLogger(NoticeService::class.java)

    @Value("\${file.upload.path}")
    lateinit var root: String

    @set:Autowired
    lateinit var noticeRepository: NoticeRepository

    /**
     * 공지사항 저장
     */
    fun save(noticeSaveDTO: NoticeSaveDTO):Notice {
        val fileList = mutableListOf<NoticeFile>()
        if (noticeSaveDTO.uploadFile!=null) {
            for (multipartFile in noticeSaveDTO.uploadFile!!) {
                // 파일 저장
                val fileDTO:FileDTO = multipartFile.save("notice", root)
                fileList.add(NoticeFile(fileDTO.fileName, multipartFile.originalFilename.toString(), fileDTO.filePath, fileDTO.fileSize ?: 0))
            }
        }
        return noticeRepository.save(Notice(noticeSaveDTO.subject, noticeSaveDTO.content,fileList))
    }

    /**
     * 공지사항 상세 조회
     */
    fun findById(noticeKey: Long):Notice {
        val optional = noticeRepository.findById(noticeKey)
        return optional.orElseThrow { CampingATSException("NOTICE.NOT_FOUND") }
    }

    /**
     * 페이지 조회
     */
    fun findByPage(noticeSearchDTO: NoticeSearchDTO): Page<NoticePageResultDTO> {
        val pageRequest = PageRequest.of(noticeSearchDTO.currentPage, noticeSearchDTO.pageSize, Sort.by("noticeKey").descending())
        return noticeRepository.findBySubjectContainsOrContentContains(noticeSearchDTO.searchKeyword, noticeSearchDTO.searchKeyword, pageRequest).map { NoticePageResultDTO(it) }
    }

    /**
     * 공지사항 업데이트
     */
    @Transactional
    fun noticeUpdate(noticeKey: Long, noticeUpdateDTO: NoticeUpdateDTO) : Notice {
        val notice = noticeRepository.findById(noticeKey).orElseThrow { CampingATSException("NOTICE.NOT_FOUND") }
        notice.subject = noticeUpdateDTO.subject
        notice.content = noticeUpdateDTO.content
        return notice
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    fun noticeDelete(noticeKey: Long) =
        noticeRepository.delete(noticeRepository.findById(noticeKey).orElseThrow { CampingATSException("NOTICE.NOT_FOUND") })

}