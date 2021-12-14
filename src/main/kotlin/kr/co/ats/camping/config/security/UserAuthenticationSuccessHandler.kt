package kr.co.ats.camping.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.utils.JWTUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.util.ObjectUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationSuccessHandler:AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        response.contentType= MediaType.APPLICATION_JSON_VALUE
        response.status=HttpStatus.OK.value()
        val authUserDTO:AuthUserDTO = authentication.principal as AuthUserDTO
        if (!ObjectUtils.isEmpty(authUserDTO)) {
            // 토큰 생성
            val token:String = JWTUtils.createToken(authUserDTO.memberId)
//            var refreshToken = JWTUtils.createRefreshToken(authUserDTO.memberId)
            response.setHeader(JWTUtils.headerString,JWTUtils.tokenPrefix+token)
//            response.setHeader(JWTUtils.refreshString, JWTUtils.tokenPrefix + refreshToken)

            ObjectMapper().writeValue(response.writer,ApiResponse.ok())
        }



    }


}