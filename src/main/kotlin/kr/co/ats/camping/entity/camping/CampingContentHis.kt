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
@Entity(name = "TB_CAMPING_CONTENT_HIS")
class CampingContentHis(
    var campingContentKey:Long,
    var version: Long,
    var content:String,
    var price:Long
    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingContentHisKey: Long? = null
}