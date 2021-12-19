package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.dto.camping.CampingSearchDTO
import kr.co.ats.camping.entity.camping.CampingInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface CampingInfoRepositoryCustom {

    fun findCamping():List<CampingInfo>
    fun findByPage(campingSearchDTO: CampingSearchDTO, pageRequest: PageRequest): Page<CampingInfo>?
}