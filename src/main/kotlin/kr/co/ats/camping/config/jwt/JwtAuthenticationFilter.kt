package kr.co.ats.camping.config.jwt

import kr.co.ats.camping.dto.user.AuthUserDTO
import kr.co.ats.camping.service.member.MemberService
import kr.co.ats.camping.utils.JWTUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(authManager:AuthenticationManager,private val memberService: MemberService) :
    BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestHeader: String? = request.getHeader(JWTUtils.headerString)
        if (requestHeader != null && requestHeader.startsWith(JWTUtils.tokenPrefix)) {
            val authToken = requestHeader.substring(7)
            val memberId = JWTUtils.extractEmail(JWTUtils.verity(authToken))
            if (memberId != null && SecurityContextHolder.getContext().authentication == null) {
                val authUserDTO: AuthUserDTO = memberService.loadUserByUsername(memberId) as AuthUserDTO
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.authorities)
            }
        }else{
            filterChain.doFilter(request, response)
            return
        }
        filterChain.doFilter(request, response)

    }
}
