package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.code.Season
import org.springframework.web.multipart.MultipartFile

data class CampingReviewSaveDTO(
    @ApiModelProperty(value = "평점", required = true)
    val rating: Long,
    @ApiModelProperty(value = "리뷰", required = true)
    val review: String,
    @ApiModelProperty(value = "계절", required = true)
    val season: Season,
    @ApiModelProperty(value = "첨부 파일")
    var uploadFileList: List<MultipartFile>?
)
