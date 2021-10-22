package kr.co.ats.camping.repository.member

import kr.co.ats.camping.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberRepository: JpaRepository<Member,Long>{


    fun findByMemberId(memberId: String): Optional<Member>

}
