package kr.co.ats.camping.config.security

import kr.co.ats.camping.config.exception.CampingATSException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationFailureHandler :AuthenticationFailureHandler {

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse, exception: AuthenticationException?) {
        throw CampingATSException("로그인 정보가 없음")
    }
}