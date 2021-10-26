package kr.co.ats.camping.entity.notice

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_NOTICE")
class Notice(var subject:String,var content:String): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var noticeKey: Long? = null
}