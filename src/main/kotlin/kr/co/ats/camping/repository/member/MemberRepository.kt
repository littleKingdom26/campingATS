package kr.co.ats.camping.repository.member

import kr.co.ats.camping.entity.TbMember
import org.springframework.data.jpa.repository.JpaRepository


interface MemberRepository: JpaRepository<TbMember,Long>