package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiModelProperty

data class NoticeSaveDTO(
    @ApiModelProperty(value = "제목",example = "제목들어갑니다.")
    var subject:String,
    @ApiModelProperty(value = "내용", example = "내용 들어갑니다.")
    var content:String)
