package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiModelProperty
import org.springframework.web.multipart.MultipartFile

data class NoticeSaveDTO(
    @ApiModelProperty(value = "제목",example = "제목들어갑니다.")
    var subject:String,
    @ApiModelProperty(value = "내용", example = "내용 들어갑니다.")
    var content:String,
    @ApiModelProperty(value = "첨부 파일", example = "첨부파일")
    var uploadFile: List<MultipartFile>
    )

