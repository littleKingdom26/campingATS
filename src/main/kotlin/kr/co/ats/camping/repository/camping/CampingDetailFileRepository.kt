package kr.co.ats.camping.repository.camping

import kr.co.ats.camping.entity.camping.CampingDetailFile
import org.springframework.data.jpa.repository.JpaRepository

interface CampingDetailFileRepository:JpaRepository<CampingDetailFile,Long> {
}