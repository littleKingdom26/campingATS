package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_INFO")
class CampingInfo(
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL],mappedBy = "campingInfo")
    @JoinColumn(name = "CAMPING_INFO_KEY")
    var campingDetail: CampingDetail?,
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL],mappedBy = "campingInfo")
    @JoinColumn(name = "CAMPING_INFO_KEY")
    var campingContent: CampingContent?,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campingInfo")
    var campingReviewList:MutableList<CampingReview>?
    ) : BaseTimeEntity() {
    constructor() : this(null,null,null)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingInfoKey: Long? = null
}