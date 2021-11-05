package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiModelProperty
import org.springframework.web.multipart.MultipartFile

data class NoticeFileSaveDTO(

    @ApiModelProperty(value = "첨부 파일", name = "uploadFile")
    var uploadFile: List<MultipartFile>
    )

