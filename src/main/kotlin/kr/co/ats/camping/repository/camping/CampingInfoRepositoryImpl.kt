package kr.co.ats.camping.repository.camping

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.ats.camping.entity.camping.CampingInfo
import kr.co.ats.camping.entity.camping.QCampingInfo
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class CampingInfoRepositoryImpl(
    val entityManager: EntityManager,
    val jpaQueryFactory: JPAQueryFactory):CampingInfoRepositoryCustom{
    override fun findCamping() : List<CampingInfo> {
        val campingInfoList : List<CampingInfo> = jpaQueryFactory.selectFrom(QCampingInfo.campingInfo).fetch()
        return campingInfoList
    }

}