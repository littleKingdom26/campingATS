package kr.co.ats.camping.service.member

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

    @Test
    fun paassword(){
        println(passwordEncoder.encode("1234"))

    }




}