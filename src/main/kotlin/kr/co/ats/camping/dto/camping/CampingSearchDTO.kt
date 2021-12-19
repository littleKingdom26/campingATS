package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.dto.common.SearchCommonDTO

data class CampingSearchDTO(
    @ApiModelProperty(value = "검색어", name = "searchKeyword") var searchKeyWord: String = "",
    @ApiModelProperty(value = "검색 스케일 ", name = "searchScale") var searchScale: String = "",
    @ApiModelProperty(value = "검색 지역", name = "searchArea") var searchArea: String = "",
    @ApiModelProperty(value = "평균 점수 시작", name = "searchStartRating") var searchStartRating: Long = 0L,
    @ApiModelProperty(value = "평균 점수 끝", name = "searchEndRating") var searchEndRating: Long = 0L,
) : SearchCommonDTO()
