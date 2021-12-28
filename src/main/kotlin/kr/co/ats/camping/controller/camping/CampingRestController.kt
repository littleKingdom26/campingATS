package kr.co.ats.camping.controller.camping

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.camping.CampingSaveDTO
import kr.co.ats.camping.dto.camping.CampingSearchDTO
import kr.co.ats.camping.dto.camping.CampingUpdateDTO
import kr.co.ats.camping.service.camping.CampingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

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
        log.debug("campingSaveDTO.toString() {}", campingSaveDTO.toString())
        return ApiResponse.ok(campingService.campingSave(campingSaveDTO))
    }

    @ApiOperation(value="캠핑장 이름 조회[비슷한 이름]", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value= ["/likeName"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun likeName(@RequestParam("name") name:String) : ApiResponse{
        log.debug("name : $name")
        return ApiResponse.ok(campingService.findLikeName(name))
    }

    /**
     * 캠핑장 리스트
     */
    @ApiOperation(value = "캠핑장 목록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findCampingList(campingSearchDTO: CampingSearchDTO):ApiResponse{
        log.info("CampingRestController.findCampingList")
        log.debug("campingSearchDTO.searchKeyWord : ${campingSearchDTO.searchKeyWord} ")
        log.debug("campingSearchDTO.searchStartRange: ${campingSearchDTO.searchStartRating} , ${campingSearchDTO.searchEndRating}")
        return ApiResponse.ok(campingService.findByPage(campingSearchDTO))
    }

    /**
     * 캠핑장 상세 정보
     */
    @ApiOperation(value="캠핑장 상세 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/{campingInfoKey}"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findCampingDetail(@PathVariable campingInfoKey:Long):ApiResponse{
        log.debug("$campingInfoKey")
        return ApiResponse.ok(campingService.findDetail(campingInfoKey))
    }

    /**
     * 캠핑장 수정
     */
    @ApiOperation(value = "캠핑장 수정", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PutMapping(value = ["/{campingInfoKey}"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun campingUpdate(@PathVariable campingInfoKey: Long,@RequestBody campingUpdateDTO: CampingUpdateDTO) : ApiResponse{
        log.debug("$campingInfoKey")
        log.debug("$campingUpdateDTO")
        return ApiResponse.ok(campingService.update(campingInfoKey, campingUpdateDTO))

    }



}