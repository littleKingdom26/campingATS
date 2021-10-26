package kr.co.ats.camping.config.handler

import com.google.gson.Gson
import kr.co.ats.camping.common.ApiResponse
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CampingAccessDeniedHandler(val messageSource: MessageSource): AccessDeniedHandler {

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
        val body = ApiResponse.error(messageSource.getMessage("FORBIDDEN", null, Locale.getDefault()),"FORBIDDEN")
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.characterEncoding = StandardCharsets.UTF_8.name()
        response?.writer?.write(Gson().toJson(body))
    }
}