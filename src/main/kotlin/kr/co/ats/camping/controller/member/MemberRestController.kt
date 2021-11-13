package kr.co.ats.camping.controller.member

import io.swagger.annotations.Api
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.dto.member.MemberResultDTO
import kr.co.ats.camping.dto.member.MemberUpdateDTO
import kr.co.ats.camping.service.member.MemberService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/member")
@Api(tags = ["Member API"], description = "회원 api 리스트")
class MemberRestController {

    private val log = LoggerFactory.getLogger(MemberRestController::class.java)

    @set:Autowired
    lateinit var memberService: MemberService

    @GetMapping(value=["/info"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findMemberInfo(@ApiIgnore @AuthenticationPrincipal authUserDTO:AuthUserDTO) : ApiResponse {
        return ApiResponse.ok(MemberResultDTO(authUserDTO))
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE],consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun putMember(@ApiIgnore @AuthenticationPrincipal authUserDTO:AuthUserDTO,@RequestBody memberUpdateDTO: MemberUpdateDTO) : ApiResponse =
        ApiResponse.ok(MemberResultDTO(memberService.updateMember(authUserDTO.memberKey ?: 0, memberUpdateDTO)))
}