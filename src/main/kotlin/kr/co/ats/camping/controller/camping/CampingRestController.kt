package kr.co.ats.camping.controller.camping

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.dto.camping.*
import kr.co.ats.camping.service.camping.CampingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["Camping API"], description = "캠핑장 api 리스트")
@RequestMapping("/api/camping")
class CampingRestController {

    private val log = LoggerFactory.getLogger(CampingRestController::class.java)

    @set:Autowired
    lateinit var campingService: CampingService

    /**
     * 캠핑장 등록
     */
    @ApiOperation(value = "캠핑장 등록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun register(campingSaveDTO: CampingSaveDTO): ApiResponse {
        log.info("CampingRestController.register")
        return ApiResponse.ok(campingService.campingSave(campingSaveDTO))
    }

    @ApiOperation(value="캠핑장 이름 조회[비슷한 이름]", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value= ["/likeName"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun likeName(@RequestParam("name") name:String) : ApiResponse{
        return ApiResponse.ok(campingService.findLikeName(name))
    }

    /**
     * 캠핑장 리스트
     */
    @ApiOperation(value = "캠핑장 목록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findCampingList(campingSearchDTO: CampingSearchDTO):ApiResponse{
        log.info("CampingRestController.findCampingList")
        return ApiResponse.ok(campingService.findByPage(campingSearchDTO))
    }

    /**
     * 캠핑장 상세 정보
     */
    @ApiOperation(value="캠핑장 상세 조회", notes = "## Request ##\n" + "campingInfoKey -  캠핑 정보 키 \n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/{campingInfoKey}"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findCampingDetail(@PathVariable campingInfoKey:Long):ApiResponse{
        log.info("CampingRestController.findCampingDetail")
        return ApiResponse.ok(campingService.findDetail(campingInfoKey))
    }

    /**
     * 캠핑장 수정
     */
    @ApiOperation(value = "캠핑장 수정", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PutMapping(value = ["/{campingInfoKey}"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun campingUpdate(@PathVariable campingInfoKey: Long,@RequestBody campingUpdateDTO: CampingUpdateDTO) : ApiResponse{
        log.info("CampingRestController.campingUpdate")
        return ApiResponse.ok(campingService.update(campingInfoKey, campingUpdateDTO))
    }

    /**
     * 사진파일 추가
     */
    @ApiOperation(value = "사진 파일 추가", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value = ["/fileAppend"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun campingFileAppend(campingFileUpdateDTO : CampingFileUpdateDTO):ApiResponse{
        log.debug("$campingFileUpdateDTO")
        return ApiResponse.ok(campingService.campingDetailFileAppend(campingFileUpdateDTO))
    }

    /**
     * 사진 파일 삭제
     */
    @ApiOperation(value = "사진 파일 삭제", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @DeleteMapping(value = ["/photo/{campingDetailFileKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun campingDetailFileDelete(@PathVariable campingDetailFileKey:Long): ApiResponse {
        log.debug("$campingDetailFileKey")
        campingService.campingDetailFileDelete(campingDetailFileKey)
        return ApiResponse.ok()
    }

    /**
     * 캠핑 삭제
     */
    @ApiOperation(value="캠핑장 삭제", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @DeleteMapping(value = ["/{campingInfoKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun campingDelete(@PathVariable campingInfoKey: Long, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO):ApiResponse{
        log.info("CampingRestController.campingDelete")
        log.debug("$campingInfoKey")
        campingService.deleteCampingInfo(campingInfoKey,authUserDTO)
        return ApiResponse.ok()

    }
    /**
     * 후기 작성 파일도 같이 들어감
     */
    @ApiOperation(value = "후기 등록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value=["/{campingInfoKey}"],produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun registerReview(@PathVariable campingInfoKey: Long, campingReviewSaveDTO: CampingReviewSaveDTO, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse {
        log.info("CampingRestController.registerReview")
        // 본인이 작성 한 글 확인
        campingService.reviewSave(campingInfoKey,campingReviewSaveDTO,authUserDTO)
        log.debug("$campingInfoKey")
        log.debug("$campingReviewSaveDTO")
        return ApiResponse.error()
    }

    /**
     * 후기 목록
     */

    /**
     * 후기 삭제
     */


    /**
     * 후기 파일 추가
     */

    /**
     * 후기 파일 삭제
     */
}
