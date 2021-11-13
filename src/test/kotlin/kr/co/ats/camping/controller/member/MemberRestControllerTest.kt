package kr.co.ats.camping.controller.member

import com.google.gson.Gson
import kr.co.ats.camping.dto.member.MemberUpdateDTO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import javax.transaction.Transactional

@Transactional
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MemberRestControllerTest{

    private val log = LoggerFactory.getLogger(MemberRestControllerTest::class.java)

    lateinit var mockMvc: MockMvc

    @set:Autowired
    lateinit var ctx: WebApplicationContext

    @set:Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply { SecurityMockMvcConfigurers.springSecurity() }
            .build()
    }


    @Test
    @WithUserDetails("taeho_user")
    @DisplayName("유저 상세 조회")
    fun userDetail() {
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/member/info")
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("태호"))
            .andDo(MockMvcResultHandlers.print())

    }


    @Test
    @WithUserDetails("taeho_user")
    @DisplayName("유저 정보 수정 닉네임만")
    fun userUpdate_nickName() {

        val nickName = "사람"
        val memberUpdateDTO=MemberUpdateDTO(null, nickName)

        val toJson = Gson().toJson(memberUpdateDTO)
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/member")
                    .content(toJson)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value(nickName))
            .andDo(MockMvcResultHandlers.print())

    }
}