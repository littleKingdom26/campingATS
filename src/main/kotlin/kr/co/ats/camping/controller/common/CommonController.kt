package kr.co.ats.camping.controller.common

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.ats.camping.code.Role
import kr.co.ats.camping.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/code")
@Api(tags = ["CODE API"], description = "코드 조회 api 리스트")
class CommonController {

    private val log = LoggerFactory.getLogger(CommonController::class.java)

    @ApiOperation(value = "권한 코드 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value=["/role"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findRoleCode(): ApiResponse {
        return ApiResponse.okMessage(Role.values().associate { it.name to it.code }, "message")
    }


}