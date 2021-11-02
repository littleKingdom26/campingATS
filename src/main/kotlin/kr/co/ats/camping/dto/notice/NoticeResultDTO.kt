package kr.co.ats.camping.dto.notice

import kr.co.ats.camping.controller.notice.NoticeRestController
import kr.co.ats.camping.entity.notice.Notice
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo

data class NoticeResultDTO(
    var noticeKey: Long? = null,
    var subject: String,
    var content: String,
    var fileList: List<NoticeFileResultDTO?>?,
    var _links: Link? = null
) {
    constructor(notice: Notice) : this(
        noticeKey = notice.noticeKey,
        subject = notice.subject,
        content = notice.content,
        fileList = notice.fileList?.map { NoticeFileResultDTO(it) },
        _links = linkTo<NoticeRestController> { noticeDetail(notice.noticeKey!!) }.withSelfRel())

}
