package kr.co.ats.camping.config.security

import com.google.gson.Gson
import kr.co.ats.camping.common.ApiResponse
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationFailureHandler(val messageSource: MessageSource) :AuthenticationFailureHandler {

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse, exception: AuthenticationException?) {
        val body = ApiResponse.error(messageSource.getMessage("LOGIN.ERROR", null, Locale.getDefault()), "LOGIN.ERROR")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(Gson().toJson(body))
    }
}