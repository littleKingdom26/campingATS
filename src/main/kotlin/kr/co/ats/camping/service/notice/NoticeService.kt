package kr.co.ats.camping.service.notice

import kr.co.ats.camping.code.Path
import kr.co.ats.camping.config.exception.CampingATSException
import kr.co.ats.camping.dto.common.FileDTO
import kr.co.ats.camping.dto.common.FileResultDTO
import kr.co.ats.camping.dto.notice.*
import kr.co.ats.camping.entity.notice.Notice
import kr.co.ats.camping.entity.notice.NoticeFile
import kr.co.ats.camping.repository.notice.NoticeFileRepository
import kr.co.ats.camping.repository.notice.NoticeRepository
import kr.co.ats.camping.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.io.File
import javax.transaction.Transactional

@Service
class NoticeService {
    private val log = LoggerFactory.getLogger(NoticeService::class.java)

    @Value("\${file.upload.path}")
    lateinit var root: String

    @set:Autowired
    lateinit var noticeRepository: NoticeRepository

    @set:Autowired
    lateinit var noticeFileRepository: NoticeFileRepository

    /**
     * 공지사항 저장
     */
    fun save(noticeSaveDTO: NoticeSaveDTO):Notice {
        val fileList = mutableListOf<NoticeFile>()
        if (noticeSaveDTO.uploadFile!=null) {
            for (multipartFile in noticeSaveDTO.uploadFile!!) {
                // 파일 저장
                val fileDTO:FileDTO = multipartFile.save(Path.NOTICE.filePath, root)
                fileList.add(NoticeFile(fileDTO.fileName, multipartFile.originalFilename.toString(), fileDTO.filePath, fileDTO.fileSize ?: 0))
            }
        }
        return noticeRepository.save(Notice(noticeSaveDTO.subject, noticeSaveDTO.content,fileList))
    }

    /**
     * 공지사항 상세 조회
     */
    fun findById(noticeKey: Long): NoticeResultDTO {
        val optional = noticeRepository.findById(noticeKey)
        return NoticeResultDTO(optional.orElseThrow { CampingATSException("NOTICE.NOT_FOUND") })
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
    fun updateNotice(noticeKey: Long, noticeUpdateDTO: NoticeUpdateDTO) : Notice {
        val notice = noticeRepository.findById(noticeKey).orElseThrow { CampingATSException("NOTICE.NOT_FOUND") }
        notice.subject = noticeUpdateDTO.subject
        notice.content = noticeUpdateDTO.content
        return notice
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    fun deleteNotice(noticeKey: Long)  {
        val notice = noticeRepository.findById(noticeKey).orElseThrow { CampingATSException("NOTICE.NOT_FOUND") }
        notice.fileList?.forEach{it -> File(root+File.separator+it.filePath+File.separator+it.fileName).delete()}
        noticeRepository.delete(noticeRepository.findById(noticeKey).orElseThrow { CampingATSException("NOTICE.NOT_FOUND") })
    }

    /**
     * 파일 저장
     */
    @Transactional
    fun saveFile(noticeKey:Long,noticeFileSaveDTO: NoticeFileSaveDTO) {
        val notice = noticeRepository.findById(noticeKey).orElseThrow { CampingATSException("NOTICE.NOT_FOUND") }
        val fileList : MutableList<NoticeFile> = notice.fileList?:mutableListOf()
        for (multipartFile in noticeFileSaveDTO.uploadFile) {
            // 파일 저장
            val fileDTO: FileDTO = multipartFile.save(Path.NOTICE.filePath, root)
            fileList.add(NoticeFile(fileDTO.fileName, multipartFile.originalFilename.toString(), fileDTO.filePath, fileDTO.fileSize ?: 0))
        }
        notice.fileList = fileList
    }

    /**
     * 파일 삭제
     */
    @Transactional
    fun deleteFile(noticeFileKey: Long) {
        val noticeFile:NoticeFile = noticeFileRepository.findById(noticeFileKey).orElseThrow { CampingATSException("NOTICE_FILE.NOT_FOUND") }
        File(root + File.separator + noticeFile.filePath + File.separator + noticeFile.fileName).delete()
        noticeFileRepository.delete(noticeFile)
    }

    fun findByFile(noticeFileKey: Long) : FileResultDTO {
        val noticeFile: NoticeFile = noticeFileRepository.findById(noticeFileKey).orElseThrow { CampingATSException("NOTICE_FILE.NOT_FOUND") }
        return FileResultDTO(File(root + File.separator + noticeFile.filePath + File.separator + noticeFile.fileName), noticeFile.originalFileName)
    }
}