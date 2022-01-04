package kr.co.ats.camping.entity.camping

import kr.co.ats.camping.dto.camping.CampingUpdateDTO
import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_CAMPING_CONTENT")
class CampingContent(
    var content:String,
    var price:Long,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPING_INFO_KEY")
    val campingInfo: CampingInfo
):BaseTimeEntity() {
    fun update(campingUpdateDTO: CampingUpdateDTO) {
        content = campingUpdateDTO.content
        price = campingUpdateDTO.price
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var campingContentKey: Long? = null


}