package kr.co.ats.camping.dto.member

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.entity.member.Member

data class MemberResultDTO(
    @ApiModelProperty(value = "회원 아이디", example = "memberId")
    var memberId:String,
    @ApiModelProperty(value = "회원 닉네임", example = "nickName")
    val nickName:String) {
    constructor(member: Member) : this(memberId = member.memberId,nickName=member.nickName)
    constructor(authUserDTO: AuthUserDTO) : this(memberId = authUserDTO.memberId, nickName = authUserDTO.nickName?:"")
}
