package kr.co.ats.camping.entity.notice

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_NOTICE")
class Notice(
    var subject:String,
    var content:String,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "NOTICE_KEY")
    var fileList: MutableList<NoticeFile>? = null): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var noticeKey: Long? = null
}