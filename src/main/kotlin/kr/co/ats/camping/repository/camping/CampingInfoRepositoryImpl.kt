package kr.co.ats.camping.repository.camping

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.ats.camping.code.Scale
import kr.co.ats.camping.dto.camping.CampingSearchDTO
import kr.co.ats.camping.entity.camping.CampingInfo
import kr.co.ats.camping.entity.camping.QCampingInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CampingInfoRepositoryImpl(
    val jpaQueryFactory: JPAQueryFactory
): QuerydslRepositorySupport(CampingInfo::class.java) ,CampingInfoRepositoryCustom {
    override fun findCamping() : List<CampingInfo> {
        val campingInfoList : List<CampingInfo> = jpaQueryFactory.selectFrom(QCampingInfo.campingInfo).fetch()
        return campingInfoList
    }

    override fun findByPage(campingSearchDTO: CampingSearchDTO, pageRequest: PageRequest): Page<CampingInfo>? {
        val jpaQueryFactory = jpaQueryFactory.selectFrom(QCampingInfo.campingInfo).where(
            containsKeyWord(campingSearchDTO.searchKeyWord),
            betweenAvgScore(campingSearchDTO.searchStartRating,campingSearchDTO.searchEndRating),
            eqScale(campingSearchDTO.searchScale),
            containsArea(campingSearchDTO.searchArea)
        )
        val campingInfoList: MutableList<CampingInfo>? = querydsl?.applyPagination(pageRequest, jpaQueryFactory)?.fetch()
        return PageImpl(campingInfoList as List<CampingInfo>, pageRequest, campingInfoList.size.toLong())
    }

    private fun containsKeyWord(keyword: String?): BooleanExpression? {
        return if (keyword.isNullOrBlank()) {
            null
        }else{
            val campingInfo = QCampingInfo.campingInfo
            campingInfo.campingDetail.campingName.contains(keyword)
                .or(campingInfo.campingDetail.address.contains(keyword))
                .or(campingInfo.campingDetail.addressDetail.contains(keyword))
        }
    }

    private fun betweenAvgScore(startRange:Long, endRange:Long):BooleanExpression?{
        return if(startRange == 0L && endRange == 0L) {
            null
        }else{
            val compingInfo = QCampingInfo.campingInfo
            compingInfo.avgRating.between(startRange,endRange)
        }
    }

    private fun eqScale(searchScale:Scale?):BooleanExpression?{
        return if (searchScale != null) {
            val campingInfo = QCampingInfo.campingInfo
            campingInfo.campingDetail.scale.eq(searchScale.name)
        }else{
            null
        }
    }

    private fun containsArea(searchArea:String?):BooleanExpression?{
        return if(searchArea.isNullOrBlank()){
            null
        }else{
            QCampingInfo.campingInfo.campingDetail.address.contains(searchArea)
        }
    }
}