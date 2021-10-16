package kr.co.ats.camping.repository.member

import kr.co.ats.camping.entity.TbMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberRepository: JpaRepository<TbMember,Long>{


    fun findByMemberId(memberId: String): Optional<TbMember>

}
