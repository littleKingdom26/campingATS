package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiParam
import org.springframework.web.multipart.MultipartFile

data class NoticeSaveDTO(
    @ApiParam(value = "제목")
    var subject:String,

    @ApiParam(value = "내용")
    var content:String,

    @ApiParam(value = "첨부 파일")
    var uploadFile: List<MultipartFile>?
    )

