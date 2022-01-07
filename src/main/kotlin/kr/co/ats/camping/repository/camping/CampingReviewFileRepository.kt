package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingReviewFile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CampingReviewFileRepository : JpaRepository<CampingReviewFile, Long> {

    fun findByCampingReviewFileKeyAndCampingReview_CampingReviewKeyAndCampingReview_CampingInfo_CampingInfoKey(campingReviewFileKey: Long, campingReviewKey: Long, campingInfoKey: Long): Optional<CampingReviewFile>


}