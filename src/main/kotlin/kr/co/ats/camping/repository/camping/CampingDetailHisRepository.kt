package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingDetail
import kr.co.ats.camping.entity.camping.CampingDetailHis
import org.springframework.data.jpa.repository.JpaRepository

interface CampingDetailHisRepository:JpaRepository<CampingDetailHis,Long> {

    fun countByCampingDetail(campingDetail: CampingDetail): Long


}