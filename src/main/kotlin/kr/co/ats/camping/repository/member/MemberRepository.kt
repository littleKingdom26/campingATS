package kr.co.ats.camping.repository.member

import kr.co.ats.camping.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository


interface MemberRepository: JpaRepository<Member,Long>{

    fun findByMemberId(memberId: String): Member?

}
