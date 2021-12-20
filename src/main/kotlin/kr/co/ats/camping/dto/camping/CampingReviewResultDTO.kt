package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.code.Season
import kr.co.ats.camping.entity.camping.CampingReview

data class CampingReviewResultDTO(
    var campingReviewKey:Long?,
    var rating:Long?,
    var review:String?,
    var season:Season?,
    var fileList: List<CampingReviewFileResultDTO>?
) {
    constructor(campingReview: CampingReview) : this(
        campingReviewKey= campingReview.campingReviewKey,
        rating = campingReview.rating,
        review = campingReview.review,
        season = Season.valueOf(campingReview.season),
        fileList = campingReview.campingReviewList?.map { CampingReviewFileResultDTO(it) }?.sortedBy { campingReviewFileResultDTO -> campingReviewFileResultDTO.campingReviewFileKey }
    )
}

