package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiParam
import kr.co.ats.camping.code.Scale
import org.springframework.web.multipart.MultipartFile

data class CampingSaveDTO(
    @ApiParam(value="캠핑장 이름",name="campingName", required = true)
    val campingName:String,
    @ApiParam(value = "캠핑장 규모", name = "scale")
    val scale: Scale,
    @ApiParam(value = "캠핑장 주소", name = "address", required = true)
    val address:String,
    @ApiParam(value = "캠핑장 상세 주소", name = "addressDetail")
    val addressDetail:String,
    @ApiParam(value = "위도", name = "latitude")
    val latitude:String,
    @ApiParam(value = "경도", name = "longitude")
    val longitude:String,
    @ApiParam(value = "내용", name = "content")
    val content:String,
    @ApiParam(value = "가격", name = "price")
    val price:Long,
    @ApiParam(value = "첨부 파일", name = "uploadFile")
    var uploadFile: List<MultipartFile>?
)
