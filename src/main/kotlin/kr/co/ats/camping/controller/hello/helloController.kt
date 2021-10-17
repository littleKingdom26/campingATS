package kr.co.ats.camping.controller.hello

import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.user.AuthUserDTO
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class helloController {

    private val log = LoggerFactory.getLogger(helloController::class.java)

    @GetMapping("/hello")
    fun hello(@AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse {
        log.debug("memberKey", authUserDTO.memberKey)
        log.debug("memberId", authUserDTO.memberId)
        log.debug("nickName", authUserDTO.nickName)
        return ApiResponse.okMessage("world","message")
    }

}

