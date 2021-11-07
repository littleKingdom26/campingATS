package kr.co.ats.camping.repository.notice

import kr.co.ats.camping.entity.notice.NoticeFile
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeFileRepository: JpaRepository<NoticeFile, Long> {

}