package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_DETAIL")
class CampingDetail(var campingName:String,
                    var scale:String,
                    var address:String,
                    var addressDetail:String,
                    var latitude:String,
                    var longitude:String,
                    @OneToOne(fetch = FetchType.LAZY)
                    @JoinColumn(name = "CAMPING_INFO_KEY")
                    val campingInfo: CampingInfo,
                    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "campingDetail")
                    var campingDetailFileList: MutableList<CampingDetailFile>? = null
                    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingDetailKey: Long? = null


}