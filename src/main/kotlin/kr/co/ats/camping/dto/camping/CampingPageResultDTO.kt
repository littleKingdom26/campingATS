package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.entity.camping.CampingInfo

data class CampingPageResultDTO(
    var content: String?,
    var campingName:String?,
    var avgRating: Double,
    var campingInfoKey:Long?,
    var scale:String?,
    var fileList: List<CampingDetailFileResultDTO>?
) {
    constructor(campingInfo: CampingInfo) : this(
        content = campingInfo.campingContent?.content,
        campingName = campingInfo.campingDetail?.campingName,
        avgRating = campingInfo.avgRating,
        campingInfoKey = campingInfo.campingInfoKey,
        scale= campingInfo.campingDetail?.scale,
        fileList = campingInfo.campingDetail?.campingDetailFileList?.map { CampingDetailFileResultDTO(it) }
    )
}

