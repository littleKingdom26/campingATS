package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.code.Scale
import kr.co.ats.camping.dto.common.SearchCommonDTO

data class CampingSearchDTO(
    @ApiModelProperty(value = "검색어") var searchKeyWord: String = "",
    @ApiModelProperty(value = "검색 스케일") var searchScale: Scale?,
    @ApiModelProperty(value = "검색 지역") var searchArea: String = "",
    @ApiModelProperty(value = "평균 점수 시작") var searchStartRating: Long = 0L,
    @ApiModelProperty(value = "평균 점수 끝") var searchEndRating: Long = 0L,
) : SearchCommonDTO()
