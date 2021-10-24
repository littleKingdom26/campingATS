package kr.co.ats.camping.service.member

import kr.co.ats.camping.config.exception.CampingATSException
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.entity.Member
import kr.co.ats.camping.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService:UserDetailsService {
    private val log = LoggerFactory.getLogger(MemberService::class.java)

    @set:Autowired lateinit var memberRepository: MemberRepository

    @Autowired lateinit var messageSource: MessageSource

    /**
     * 유저 정보 조회
     */
    override fun loadUserByUsername(username: String): UserDetails {
        // 유저 조회가 필요함
        log.debug("username : {}",username)
        val loginUser: Member? = memberRepository.findByMemberId(username)
        if (loginUser != null) {
            val user: Member = loginUser
            return AuthUserDTO(user.memberId, user.memberPassword, user.memberKey, user.nickName, user.role)
        }else{
            log.debug("message {}", messageSource.getMessage("LOGIN.ERROR", null, Locale.getDefault()))
            throw CampingATSException("LOGIN.ERROR")
        }

    }




}