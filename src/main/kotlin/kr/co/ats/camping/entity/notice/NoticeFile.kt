package kr.co.ats.camping.entity.notice

import kr.co.ats.camping.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_NOTICE_FILE")
class NoticeFile(
    var fileName:String,
    var originalFileName:String,
    var filePath:String,
    var fileSize:Long
):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var noticeFileKey: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTICE_KEY")
    lateinit var notice: Notice
}