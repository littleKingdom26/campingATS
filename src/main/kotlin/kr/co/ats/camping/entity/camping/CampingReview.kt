package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_REVIEW")
class CampingReview(
    var rating:Long,
    var review:String,
    var season:String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPING_INFO_KEY")
    var campingInfo: CampingInfo,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campingReview")
    var campingReviewList :MutableList<CampingReviewFile>?
) :BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingReviewKey: Long? = null
}