package kr.co.ats.camping.config.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationFilter(authenticationManager: AuthenticationManager): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {


        val userName:String?=obtainUsername(request)
        val password:String?=obtainPassword(request)

        val token:UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userName,password)



        return authenticationManager.authenticate(token)
    }
}