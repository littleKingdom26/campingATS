package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.dto.common.SearchCommonDTO

data class NoticeSearchDTO(@ApiModelProperty(value = "검색어") var searchKeyword:String = ""):SearchCommonDTO()
