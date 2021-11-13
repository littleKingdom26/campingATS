package kr.co.ats.camping.service.member

import kr.co.ats.camping.dto.member.MemberUpdateDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional


@Transactional
@SpringBootTest
internal class MemberServiceTest{
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var memberService: MemberService

    @Test
    fun MemberUpdate(){

        val password = "4321"
        val memberUpdateDTO = MemberUpdateDTO(password, "nickName")
        val updateMember = memberService.updateMember(41, memberUpdateDTO)

        println(passwordEncoder.matches(password, updateMember.memberPassword))


    }

}