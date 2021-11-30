package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_DETAIL_HIS")
class CampingDetailHis(
    var campingDetailKey:Long,
    var version:Long,
    var campingName:String,
    var scale:String,
    var address:String,
    var addressDetail:String,
    var latitude:String,
    var longitude:String
    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingDetailHisKey: Long? = null


}