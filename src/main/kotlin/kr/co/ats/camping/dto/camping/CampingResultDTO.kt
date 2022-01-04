package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.entity.camping.CampingContent
import kr.co.ats.camping.entity.camping.CampingDetail
import kr.co.ats.camping.entity.camping.CampingInfo

data class CampingResultDTO(
    var campingInfoKey: Long?,
    var content: String?,
    var price: Long?,
    var campingName: String?,
    var address: String?,
    var addressDetail: String?,
    var autoYn: String?,
    var regId : String,
    var fileList: List<CampingDetailFileResultDTO?>?
){
    constructor(campingInfo:CampingInfo,campingContent: CampingContent,campingDetail: CampingDetail,campingDetailFile: MutableList<CampingDetailFileResultDTO>):this(
        campingInfo.campingInfoKey,
        campingContent.content,
        campingContent.price,
        campingDetail.campingName,
        campingDetail.address,
        campingDetail.addressDetail,
        campingDetail.autoYn,
        campingInfo.regId,
        campingDetailFile)

    constructor(campingInfo:CampingInfo) : this(
        campingInfoKey = campingInfo.campingInfoKey,
        content = campingInfo.campingContent?.content,
        price  = campingInfo.campingContent?.price,
        campingName = campingInfo.campingDetail?.campingName,
        address = campingInfo.campingDetail?.address,
        addressDetail = campingInfo.campingDetail?.addressDetail,
        autoYn = campingInfo.campingDetail?.autoYn,
        regId = campingInfo.regId,
        fileList = campingInfo.campingDetail?.campingDetailFileList?.map { CampingDetailFileResultDTO(it) }?.sortedBy { campingDetailFileResultDTO -> campingDetailFileResultDTO.campingDetailFileKey }
    )
}

