package kr.co.ats.camping.dto.camping

import kr.co.ats.camping.entity.camping.CampingDetailFile

data class CampingDetailFileResultDTO (
    var campingDetailFileKey:Long?,
    var fileName:String,
    var fileSize:Long,
    var filePath:String) {
    constructor(campingDetailFile: CampingDetailFile) : this(campingDetailFile.campingDetailFileKey, campingDetailFile.fileName,campingDetailFile.fileSize,campingDetailFile.filePath)
}