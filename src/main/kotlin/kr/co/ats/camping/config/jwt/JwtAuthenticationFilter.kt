package kr.co.ats.camping.config.jwt

import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.service.member.UserService
import kr.co.ats.camping.utils.JWTUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(authManager:AuthenticationManager,private val userService: UserService) :
    BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestHeader: String? = request.getHeader(JWTUtils.headerString)
        val refreshHeader: String? = request.getHeader(JWTUtils.refreshString)
        if (requestHeader != null && requestHeader.startsWith(JWTUtils.tokenPrefix)) {
            // 그냥 토큰
            val authToken = requestHeader.substring(7)
            val memberId = JWTUtils.extractEmail(JWTUtils.verity(authToken))
            if (SecurityContextHolder.getContext().authentication == null) {
                val authUserDTO: AuthUserDTO = userService.loadUserByUsername(memberId) as AuthUserDTO
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.authorities)
            }
        } else if (refreshHeader != null && refreshHeader.startsWith(JWTUtils.tokenPrefix)) {
            // 리플래쉬 토큰일때는????
        } else{
            filterChain.doFilter(request, response)
            return
        }
        filterChain.doFilter(request, response)

    }
}
