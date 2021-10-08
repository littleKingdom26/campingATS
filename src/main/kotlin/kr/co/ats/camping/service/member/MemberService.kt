package kr.co.ats.camping.service.member

import kr.co.ats.camping.repository.member.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService(private val memberRepository: MemberRepository):UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        // 유저 조회가 필요함
        memberRepository.findById(1L)

        return User("dd","dd", Collections.singleton(SimpleGrantedAuthority("ROLE_ADMIN")))
    }
}