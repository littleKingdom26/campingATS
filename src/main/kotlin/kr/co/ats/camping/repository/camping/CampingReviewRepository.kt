package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingInfo
import kr.co.ats.camping.entity.camping.CampingReview
import org.springframework.data.jpa.repository.JpaRepository

interface CampingReviewRepository : JpaRepository<CampingReview, Long> {

    fun countBySeasonAndRegIdAndCampingInfo(season: String, regId: String, campingInfo: CampingInfo): Long

}