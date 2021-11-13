package kr.co.ats.camping.service.member

import kr.co.ats.camping.config.exception.CampingATSException
import kr.co.ats.camping.dto.member.MemberUpdateDTO
import kr.co.ats.camping.entity.member.Member
import kr.co.ats.camping.repository.member.MemberRepository
import kr.co.ats.camping.utils.encodePassword
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class MemberService {

    private val log = LoggerFactory.getLogger(MemberService::class.java)

    @set:Autowired
    lateinit var memberRepository: MemberRepository

    @set:Autowired
    lateinit var passwordEncoder: PasswordEncoder


    @Transactional
    fun updateMember(memberKey: Long, memberUpdateDTO: MemberUpdateDTO): Member {
        val member = memberRepository.findById(memberKey).orElseThrow { throw CampingATSException("MEMBER.NOT_FOUND") }

        if (memberUpdateDTO.password?.isNotEmpty() == true) {
            val encodePassword = memberUpdateDTO.password?.encodePassword(passwordEncoder)
            log.debug("$encodePassword")
            member.memberPassword = encodePassword!!
        }

        if (memberUpdateDTO.nickName?.isNotEmpty() == true) {
            member.nickName = memberUpdateDTO.nickName!!
        }
        return member
    }
}