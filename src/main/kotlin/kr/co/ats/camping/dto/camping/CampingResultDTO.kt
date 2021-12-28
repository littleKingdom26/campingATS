package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.entity.camping.CampingContent
import kr.co.ats.camping.entity.camping.CampingDetail
import kr.co.ats.camping.entity.camping.CampingInfo

data class CampingResultDTO(var content:String?,
                            var price:Long?,
                            var campingName:String?,
                            var address:String?,
                            var addressDetail:String?,
                            var autoYn:String?,
                            var fileList: List<CampingDetailFileResultDTO?>?
){
    constructor(campingContent: CampingContent,campingDetail: CampingDetail,campingDetailFile: MutableList<CampingDetailFileResultDTO>):this(
        campingContent.content, campingContent.price,campingDetail.campingName,campingDetail.address,campingDetail.addressDetail,campingDetail.autoYn, campingDetailFile)

    constructor(campingInfo:CampingInfo) : this(
        content = campingInfo.campingContent?.content,
        price  = campingInfo.campingContent?.price,
        campingName = campingInfo.campingDetail?.campingName,
        address = campingInfo.campingDetail?.address,
        addressDetail = campingInfo.campingDetail?.addressDetail,
        autoYn = campingInfo.campingDetail?.autoYn,
        null
    )
}

