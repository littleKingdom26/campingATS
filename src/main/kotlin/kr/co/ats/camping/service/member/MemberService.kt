package kr.co.ats.camping.service.member

import kr.co.ats.camping.config.security.UserAuthenticationFilter
import kr.co.ats.camping.dto.user.AuthUserDTO
import kr.co.ats.camping.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberRepository: MemberRepository):UserDetailsService {
    private val log = LoggerFactory.getLogger(MemberService::class.java)

    /**
     * 유저 정보 조회
     */
    override fun loadUserByUsername(username: String?): UserDetails {
        // 유저 조회가 필요함
        log.debug("username : {}",username)
//        memberRepository.findByMemberIdEquals(username)
        return AuthUserDTO("userId", "password", 1L, "nickName", "ROLE_ADMIN")
    }
}