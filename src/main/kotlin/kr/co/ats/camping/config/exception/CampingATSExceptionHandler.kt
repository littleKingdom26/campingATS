package kr.co.ats.camping.config.exception

import kr.co.ats.camping.common.ApiResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
@ControllerAdvice
class CampingATSExceptionHandler {


    @ExceptionHandler(CampingATSException::class)
    fun campingATSException(e:CampingATSException):ApiResponse{
        return ApiResponse.error(e.message)
    }
}