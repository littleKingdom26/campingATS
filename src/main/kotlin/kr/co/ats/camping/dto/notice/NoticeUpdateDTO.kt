package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiModelProperty

data class NoticeUpdateDTO(
    @ApiModelProperty(value = "제목", name = "subject",example = "제목을 수정해봅시다.")
    var subject:String,

    @ApiModelProperty(value = "내용", name = "content",example = "내용을 수정해봅시다.")
    var content:String
    )

