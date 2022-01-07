package kr.co.ats.camping.dto.camping

import io.swagger.annotations.ApiParam
import org.springframework.web.multipart.MultipartFile

data class CampingFileUpdateDTO(
    @ApiParam(value = "캠핑 정보 키")
    var campingInfoKey: Long,
    @ApiParam(value = "첨부 파일")
    var uploadFile: MultipartFile
)
