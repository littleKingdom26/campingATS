package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.entity.camping.CampingDetailFile
import kr.co.ats.camping.entity.camping.CampingInfo

data class CampingPageResultDTO(
    var content: String?,
    var campingName:String?,
    var avgRating: Long,
    var fileList: MutableList<CampingDetailFile>?
) {
    constructor(campingInfo: CampingInfo) : this(
        content = campingInfo.campingContent?.content,
        campingName = campingInfo.campingDetail?.campingName,
        avgRating = campingInfo.avgRating,
        fileList = campingInfo.campingDetail?.campingDetailFileList
    )
}

