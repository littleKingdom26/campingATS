package kr.co.ats.camping.config.jwt

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {

    private val log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        TODO("Not yet implemented")
        val body = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authException.message ?: "")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(Gson().toJson(body))
    }
}