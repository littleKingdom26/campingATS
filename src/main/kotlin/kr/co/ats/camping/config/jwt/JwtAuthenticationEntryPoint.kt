package kr.co.ats.camping.config.jwt

import com.google.gson.Gson
import kr.co.ats.camping.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationEntryPoint(val messageSource: MessageSource): AuthenticationEntryPoint {

    private val log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {


        val body = ApiResponse.error(messageSource.getMessage("UNAUTHORIZED", null, Locale.getDefault()), "UNAUTHORIZED")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(Gson().toJson(body))
    }
}