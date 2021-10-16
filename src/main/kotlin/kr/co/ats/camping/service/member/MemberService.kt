package kr.co.ats.camping.service.member

import kr.co.ats.camping.dto.user.AuthUserDTO
import kr.co.ats.camping.entity.TbMember
import kr.co.ats.camping.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService(private val memberRepository: MemberRepository):UserDetailsService {
    private val log = LoggerFactory.getLogger(MemberService::class.java)


    /**
     * 유저 정보 조회
     */
    override fun loadUserByUsername(username: String): UserDetails {
        // 유저 조회가 필요함
        log.debug("username : {}",username)
        val loginUser: Optional<TbMember> = memberRepository.findByMemberId(username)

        var user:TbMember = loginUser.orElseThrow()

        log.debug("user.memberPassword {} ", user.memberPassword)


//        memberRepository.findByMemberIdEquals(username)
        return AuthUserDTO(user.memberId, user.memberPassword, user.memberKey, user.nickName, user.role)
    }
}