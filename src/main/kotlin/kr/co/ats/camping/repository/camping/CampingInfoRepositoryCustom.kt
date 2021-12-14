package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingInfo

interface CampingInfoRepositoryCustom {

    fun findCamping():List<CampingInfo>
}