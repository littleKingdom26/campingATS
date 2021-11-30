package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingContent
import org.springframework.data.jpa.repository.JpaRepository

interface CampingContentRepository: JpaRepository<CampingContent,Long> {
}