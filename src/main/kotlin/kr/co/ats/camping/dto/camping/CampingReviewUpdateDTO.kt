package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.code.Season

data class CampingReviewUpdateDTO(
    @ApiModelProperty(value = "평점", required = true)
    var rating: Long,
    @ApiModelProperty(value = "리뷰", required = true)
    var review: String,
    @ApiModelProperty(value = "계절", required = true)
    var season: Season
)
