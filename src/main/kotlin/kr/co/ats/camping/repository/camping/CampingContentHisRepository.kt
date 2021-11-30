package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingContentHis
import org.springframework.data.jpa.repository.JpaRepository

interface CampingContentHisRepository: JpaRepository<CampingContentHis,Long> {
}