package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.code.CodeYn
import kr.co.ats.camping.code.Scale
import org.springframework.web.multipart.MultipartFile

data class CampingSaveDTO(
    @ApiModelProperty(value="캠핑장 이름", required = true)
    val campingName:String,
    @ApiModelProperty(value = "캠핑장 규모")
    val scale: Scale,
    @ApiModelProperty(value = "캠핑장 주소", required = true)
    val address:String,
    @ApiModelProperty(value = "캠핑장 상세 주소")
    val addressDetail:String,
    @ApiModelProperty(value = "위도")
    val latitude:String,
    @ApiModelProperty(value = "경도")
    val longitude:String,
    @ApiModelProperty(value = "내용")
    val content:String,
    @ApiModelProperty(value = "가격")
    val price:Long,
    @ApiModelProperty(value = "코드_여부")
    val autoYn: CodeYn,
    @ApiModelProperty(value = "첨부 파일")
    var uploadFileList: List<MultipartFile>?
)
