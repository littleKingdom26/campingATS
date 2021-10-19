package kr.co.ats.camping.controller.join

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.member.MemberSaveDTO
import kr.co.ats.camping.service.member.MemberService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["JOIN API"], description = "회원가입 api 리스트")
@RequestMapping("/api")
class JoinRestController(val memberService: MemberService) {
    private val log = LoggerFactory.getLogger(JoinRestController::class.java)


    @PostMapping(value = ["/join"],consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "이벤트 목록 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    fun join(@RequestBody memberSaveDTO: MemberSaveDTO):ApiResponse{
        log.debug("memberSaveDTO : "+ memberSaveDTO.toString())
        memberSaveDTO.toString()
        return ApiResponse.ok()
    }
}
