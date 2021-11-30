package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingReviewFile
import org.springframework.data.jpa.repository.JpaRepository

interface CampingReviewFileRepository : JpaRepository<CampingReviewFile, Long> {
}