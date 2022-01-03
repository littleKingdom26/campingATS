package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.entity.camping.CampingInfo

data class CampingDetailResultDTO(
    var campingInfoKey:Long?,
    var content:String?,
    var price:Long?,
    var campingName:String?,
    var address:String?,
    var addressDetail:String?,
    var latitude: String?,
    var longitude: String?,
    var avgRating: Double?,
    var regId: String,
    var reviewList: List<CampingReviewResultDTO>?,
    var detailFileList: List<CampingDetailFileResultDTO>?
) {
    constructor(campingInfo: CampingInfo) : this(
        campingInfoKey = campingInfo.campingInfoKey,
        content = campingInfo.campingContent?.content,
        price = campingInfo.campingContent?.price,
        campingName = campingInfo.campingDetail?.campingName,
        address = campingInfo.campingDetail?.address,
        addressDetail = campingInfo.campingDetail?.addressDetail,
        latitude = campingInfo.campingDetail?.latitude,
        longitude = campingInfo.campingDetail?.longitude,
        avgRating = campingInfo.avgRating,
        regId = campingInfo.regId,
        reviewList = campingInfo.campingReviewList?.map { CampingReviewResultDTO(it) }?.sortedBy { campingReviewResultDTO -> campingReviewResultDTO.campingReviewKey },
        detailFileList = campingInfo.campingDetail?.campingDetailFileList?.map { CampingDetailFileResultDTO(it) }?.sortedBy { campingDetailFileResultDTO -> campingDetailFileResultDTO.campingDetailFileKey }
    )
}

