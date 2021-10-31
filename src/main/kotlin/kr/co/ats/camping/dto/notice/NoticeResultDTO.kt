package kr.co.ats.camping.dto.notice

import kr.co.ats.camping.entity.notice.Notice
import org.springframework.hateoas.Link

data class NoticeResultDTO(var noticeKey:Long?=null,var subject:String, var content:String, var fileList: List<NoticeFileResultDTO?>? , var _links: Link?=null) {
    constructor(notice: Notice) : this(
        noticeKey = notice.noticeKey,
        subject = notice.subject,
        content = notice.content,
        fileList = notice.fileList?.map { NoticeFileResultDTO(it) })

    constructor(notice: Notice, _link: Link?) : this(
        noticeKey = notice.noticeKey,
        subject = notice.subject,
        content = notice.content,
        fileList = notice.fileList?.map { NoticeFileResultDTO(it) },
        _links = _link)
}
