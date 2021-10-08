package kr.co.ats.camping.config.jwt

import kr.co.ats.camping.service.member.MemberService
import kr.co.ats.camping.utils.JWTUtils
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class JwtAuthenticationFilter(
    private val memberService: MemberService,
    private val jwtUtils: JWTUtils) :
    OncePerRequestFilter() {
//    BasicAuthenticationFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestHeader: String? = request.getHeader("Authorization")
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            val authToken = requestHeader.substring(7)
            val memberId = jwtUtils.extractEmail(jwtUtils.verity(authToken))
            if (memberId != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = memberService.loadUserByUsername(memberId)

                /*if (jwtTokenUtils.validateToken(authToken, userDetails)) {
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = authentication
                }*/
            }

        }
        filterChain.doFilter(request, response)
    }

}
