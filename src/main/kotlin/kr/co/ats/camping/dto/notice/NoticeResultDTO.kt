package kr.co.ats.camping.dto.notice

import kr.co.ats.camping.entity.notice.Notice

data class NoticeResultDTO(var subject:String,var content:String) {
    constructor(notice: Notice) : this(subject = notice.subject,content = notice.content)
}
