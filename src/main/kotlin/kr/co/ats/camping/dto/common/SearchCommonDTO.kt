package kr.co.ats.camping.dto.common

import io.swagger.annotations.ApiModelProperty

open class SearchCommonDTO(@ApiModelProperty(value="조회 페이지",example = "0") var currentPage:Int=0, @ApiModelProperty(value = "노출 건수", example = "20")var pageSize:Int = 20) {
}