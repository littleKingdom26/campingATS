package kr.co.ats.camping.service.notice

import kr.co.ats.camping.dto.common.FileDTO
import kr.co.ats.camping.dto.notice.NoticeSaveDTO
import kr.co.ats.camping.entity.notice.Notice
import kr.co.ats.camping.entity.notice.NoticeFile
import kr.co.ats.camping.repository.notice.NoticeRepository

import kr.co.ats.camping.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NoticeService {
    private val log = LoggerFactory.getLogger(NoticeService::class.java)

    @Value("\${file.upload.path}")
    lateinit var root: String


    @set:Autowired
    lateinit var noticeRepository: NoticeRepository

    fun save(noticeSaveDTO: NoticeSaveDTO):Notice {
        val fileList = mutableListOf<NoticeFile>()
        for (multipartFile in noticeSaveDTO.uploadFile) {
            // 파일 저장
            val fileDTO:FileDTO = multipartFile.save("notice", root)
            fileList.add(NoticeFile(fileDTO.fileName, multipartFile.originalFilename.toString(), fileDTO.filePath, fileDTO.fileSize ?: 0))
        }
        return noticeRepository.save(Notice(noticeSaveDTO.subject, noticeSaveDTO.content,fileList))

    }
}