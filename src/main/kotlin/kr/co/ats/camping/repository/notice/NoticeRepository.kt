package kr.co.ats.camping.repository.notice

import kr.co.ats.camping.entity.notice.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository: JpaRepository<Notice, Long> {
}