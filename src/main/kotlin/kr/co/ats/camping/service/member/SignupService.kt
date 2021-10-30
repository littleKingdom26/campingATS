package kr.co.ats.camping.service.member

import kr.co.ats.camping.code.Role
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.config.exception.CampingATSException
import kr.co.ats.camping.dto.member.MemberResultDTO
import kr.co.ats.camping.dto.member.MemberSaveDTO
import kr.co.ats.camping.entity.member.Member
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
    fun registerUser(memberSaveDTO: MemberSaveDTO) : ApiResponse {
        val role: String = when (memberSaveDTO.role) {
            Role.ROLE_USER.code -> Role.ROLE_USER.name
            Role.ROLE_ADMIN.code -> Role.ROLE_ADMIN.name
            else -> throw CampingATSException("SIGNUP.NOT_FOUND.ROLE")
        }

        val password:String = passwordEncoder.encode(memberSaveDTO.password)
        log.debug("role :  $role")
        log.debug("password :  $password")

        return ApiResponse.ok(MemberResultDTO(memberRepository.save(Member(memberSaveDTO.memberId, memberSaveDTO.nickName, password, role))))
    }

    /**
     * 아이디 중복 조회
     */
    fun checkMemberId(checkId: String) {
        memberRepository.findByMemberId(checkId)?.let {
            throw CampingATSException("SIGNUP.ID.DUPLICATE")
        }
    }
}