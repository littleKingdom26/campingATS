package kr.co.ats.camping.entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@DynamicInsert
@DynamicUpdate
@Entity(name="TB_MEMBER")
class TbMember(var memberId:String,var memberPassword:String,var role:String) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberKey: Long?=null

    var nickName:String?=null



}