package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_CONTENT_HIS")
class CampingContentHis(
    var version: Long,
    var content:String,
    var price:Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPING_CONTENT_KEY")
    var campingContent: CampingContent
    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingContentHisKey: Long? = null
}