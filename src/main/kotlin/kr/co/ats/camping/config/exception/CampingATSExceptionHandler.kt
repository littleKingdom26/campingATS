package kr.co.ats.camping.config.exception

import kr.co.ats.camping.common.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@ControllerAdvice
class CampingATSExceptionHandler {


    @Autowired lateinit var messageSource: MessageSource

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CampingATSException::class)
    fun campingATSException(e:CampingATSException):ApiResponse{
        return ApiResponse.error(messageSource.getMessage(e.message?:"ERROR", null, Locale.getDefault()))
    }
}