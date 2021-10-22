package kr.co.ats.camping.service.member

import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.entity.Member
import kr.co.ats.camping.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService:UserDetailsService {
    private val log = LoggerFactory.getLogger(MemberService::class.java)

    @set:Autowired lateinit var memberRepository: MemberRepository

    /**
     * 유저 정보 조회
     */
    override fun loadUserByUsername(username: String): UserDetails {
        // 유저 조회가 필요함
        log.debug("username : {}",username)
        val loginUser: Optional<Member> = memberRepository.findByMemberId(username)
        val user:Member = loginUser.orElseThrow()
        return AuthUserDTO(user.memberId, user.memberPassword, user.memberKey, user.nickName, user.role)
    }




}