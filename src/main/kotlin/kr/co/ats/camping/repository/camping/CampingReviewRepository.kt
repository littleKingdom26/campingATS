package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingInfo
import kr.co.ats.camping.entity.camping.CampingReview
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CampingReviewRepository : JpaRepository<CampingReview, Long> {

    fun countBySeasonAndRegIdAndCampingInfo(season: String, regId: String, campingInfo: CampingInfo): Long


    fun findByCampingReviewKeyAndRegIdAndCampingInfo_CampingInfoKey(campingReviewKey: Long, regId: String, campingInfoKey: Long): Optional<CampingReview>



}