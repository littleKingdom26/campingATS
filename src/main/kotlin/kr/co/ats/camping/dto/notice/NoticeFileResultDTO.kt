package kr.co.ats.camping.dto.notice

import kr.co.ats.camping.entity.notice.NoticeFile

data class NoticeFileResultDTO(var fileName:String,var fileSize:Long,var filePath:String,var originalFileName:String){
    constructor(noticeFile: NoticeFile):this(fileName=noticeFile.fileName,fileSize=noticeFile.fileSize,filePath=noticeFile.filePath,originalFileName=noticeFile.originalFileName)

}
