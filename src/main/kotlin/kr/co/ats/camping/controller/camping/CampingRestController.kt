package kr.co.ats.camping.controller.camping

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.ats.camping.common.ApiResponse
import kr.co.ats.camping.dto.camping.CampingSaveDTO
import kr.co.ats.camping.service.camping.CampingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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



}