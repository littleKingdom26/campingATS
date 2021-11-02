package kr.co.ats.camping.repository.notice

import kr.co.ats.camping.entity.notice.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository: JpaRepository<Notice, Long> {

    fun findBySubjectContainsOrContentContains(subject: String, content: String, pageable: Pageable): Page<Notice>

}