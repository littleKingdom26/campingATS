package kr.co.ats.camping.dto.notice

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.controller.notice.NoticeRestController
import kr.co.ats.camping.entity.notice.Notice
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo
import java.time.format.DateTimeFormatter

data class NoticePageResultDTO(
    @ApiModelProperty(value = "공지사항_키", name = "noticeKey")
    var noticeKey: Long?=null,
    @ApiModelProperty(value = "제목", name = "subject")
    var subject: String?=null,
    @ApiModelProperty(value = "작성일", name = "regDt")
    var regDt: String?=null,
    @ApiModelProperty(value = "작성자", name = "regID")
    var regID: String?=null,
    @ApiModelProperty(value = "링크정보", name = "_links")
    var _links: Link? = null
    ){
    constructor(notice: Notice) : this(
        noticeKey = notice.noticeKey,
        subject = notice.subject,
        regDt = notice.regDt.format(DateTimeFormatter.ISO_DATE),
        regID = notice.regId,
        _links = linkTo<NoticeRestController> { noticeDetail(notice.noticeKey!!) }.withRel("detail")
    )
}
