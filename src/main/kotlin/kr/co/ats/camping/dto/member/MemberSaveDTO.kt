package kr.co.ats.camping.dto.member

import io.swagger.annotations.ApiModelProperty

data class MemberSaveDTO(
    @ApiModelProperty(value = "회원 아이디",example = "test")
    var memberId:String,
    @ApiModelProperty(value = "비밀번호",example = "1234")
    val password:String,
    @ApiModelProperty(value = "닉네임",example = "닉네임")
    val nickName:String,
    @ApiModelProperty(value = "권한",example = "ADMIN,USER")
    var role:String)