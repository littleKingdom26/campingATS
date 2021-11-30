package kr.co.ats.camping.controller.join

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.member.MemberCheckIdDTO
import kr.co.ats.camping.dto.member.MemberSaveDTO
import kr.co.ats.camping.service.member.SignupService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = ["SignUp API"], description = "회원가입 api 리스트")
@RequestMapping("/api/signUp")
class SignupRestController {
    private val log = LoggerFactory.getLogger(SignupRestController::class.java)

    @set:Autowired
    lateinit var signupService:SignupService

    @ApiOperation(value = "회원 가입", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody memberSaveDTO: MemberSaveDTO):ApiResponse{
        log.info("SignupRestController.register")
        signupService.checkMemberId(memberSaveDTO.memberId)
        return ApiResponse.ok(signupService.registerUser(memberSaveDTO))
    }

    @ApiOperation(value = "아이디 중복 체크", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value=["/checkId"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun checkId(memberCheckIdDTO:MemberCheckIdDTO):ApiResponse{
        log.debug("memberCheckId : $memberCheckIdDTO")
        signupService.checkMemberId(memberCheckIdDTO.checkId)
        return ApiResponse.ok()
    }

}
