package kr.co.ats.camping.dto.member

import io.swagger.annotations.ApiModelProperty

data class MemberUpdateDTO(
    @ApiModelProperty(value = "회원 비밀번호[변경시 입력]", example = "password")
    var password:String?,
    @ApiModelProperty(value = "회원 닉네임", example = "nickName")
    var nickName:String?
)