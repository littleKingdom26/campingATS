package kr.co.ats.camping.dto.member

import io.swagger.annotations.ApiModelProperty

data class MemberCheckIdDTO(
    @ApiModelProperty(value = "체크할 아이디", example = "admin")
    var checkId:String
    )
