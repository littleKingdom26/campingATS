package kr.co.ats.camping.entity

import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
class BaseTimeEntity {

    @CreatedDate
    lateinit var regDt: LocalDateTime

    @UpdateTimestamp
    lateinit var updDt: LocalDateTime

    @CreatedBy
    lateinit var regId: String

    @LastModifiedBy
    lateinit var updId: String




}