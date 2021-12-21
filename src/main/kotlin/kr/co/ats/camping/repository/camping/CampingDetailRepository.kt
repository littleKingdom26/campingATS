package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingDetail
import org.springframework.data.jpa.repository.JpaRepository

interface CampingDetailRepository:JpaRepository<CampingDetail,Long> {


    fun findByCampingNameContains(campingName: String): List<CampingDetail>

}