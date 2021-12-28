package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_DETAIL_HIS")
class CampingDetailHis(
    var version:Long,
    var campingName:String,
    var scale:String,
    var address:String,
    var addressDetail:String,
    var latitude:String,
    var longitude:String,
    var autoYn:String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPING_DETAIL_KEY")
    var campingDetail: CampingDetail,
    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingDetailHisKey: Long? = null


}