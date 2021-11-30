package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingInfo
import org.springframework.data.jpa.repository.JpaRepository

interface CampingInfoRepository : JpaRepository<CampingInfo, Long> {
}