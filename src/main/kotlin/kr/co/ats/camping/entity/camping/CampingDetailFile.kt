package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_DETAIL_FILE")
class CampingDetailFile(
    var fileName:String,
    var filePath:String,
    var fileSize:Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPING_DETAIL_KEY")
    var campingDetail: CampingDetail
):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingDetailFileKey: Long? = null
}