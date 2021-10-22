package kr.co.ats.camping.service.member

import kr.co.ats.camping.code.Role
import kr.co.ats.camping.dto.member.MemberResultDTO
import kr.co.ats.camping.dto.member.MemberSaveDTO
import kr.co.ats.camping.entity.Member
import kr.co.ats.camping.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignupService {

    private val log = LoggerFactory.getLogger(SignupService::class.java)


    @set:Autowired
    lateinit var memberRepository: MemberRepository

    @set:Autowired
    lateinit var passwordEncoder: PasswordEncoder

    /**
     * 회원 가입
     */
    fun registerUser(memberSaveDTO: MemberSaveDTO) : MemberResultDTO {
        val role: String = when (memberSaveDTO.role) {
            Role.ROLE_USER.code -> Role.ROLE_USER.name
            Role.ROLE_ADMIN.code -> Role.ROLE_ADMIN.name
            else -> Role.ROLE_USER.name
        }

        val password:String = passwordEncoder.encode(memberSaveDTO.password)
        log.debug("role :  $role")
        log.debug("password :  $password")

        return MemberResultDTO(memberRepository.save(Member(memberSaveDTO.memberId, memberSaveDTO.nickName, password, role)))
    }


}