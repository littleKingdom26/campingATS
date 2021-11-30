package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingReview
import org.springframework.data.jpa.repository.JpaRepository

interface CampingReviewRepository : JpaRepository<CampingReview, Long> {
}