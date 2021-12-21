package kr.co.ats.camping.dto.camping

import kr.co.ats.camping.entity.camping.CampingDetail

data class CampingLikeNameResultDTO(
    val name:String
) {
    constructor(campingDetail: CampingDetail) : this(
        campingDetail.campingName
    )
}
