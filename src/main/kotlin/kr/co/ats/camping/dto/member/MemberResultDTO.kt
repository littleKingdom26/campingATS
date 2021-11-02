package kr.co.ats.camping.dto.member

import kr.co.ats.camping.entity.member.Member

data class MemberResultDTO(
    var memberId:String,
    val nickName:String) {
    constructor(member: Member) : this(memberId = member.memberId,nickName=member.nickName)
}
