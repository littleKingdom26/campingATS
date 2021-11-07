package kr.co.ats.camping.dto.notice

import kr.co.ats.camping.entity.notice.NoticeFile

data class NoticeFileResultDTO(
    var noticeFileKey: Long,
    var fileName:String,
    var fileSize:Long,
    var filePath:String,
    var originalFileName:String){
    constructor(noticeFile: NoticeFile) : this(
        noticeFileKey = noticeFile.noticeFileKey?:0,
        fileName = noticeFile.fileName,
        fileSize = noticeFile.fileSize,
        filePath = noticeFile.filePath,
        originalFileName = noticeFile.originalFileName)


}
