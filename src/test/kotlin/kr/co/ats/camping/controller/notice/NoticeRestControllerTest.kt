package kr.co.ats.camping.controller.notice


import com.google.gson.Gson
import kr.co.ats.camping.dto.notice.NoticeSaveDTO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import javax.transaction.Transactional


@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class NoticeRestControllerTest{

    private val log = LoggerFactory.getLogger(NoticeRestControllerTest::class.java)

    lateinit var mockMvc: MockMvc

    @set:Autowired
    lateinit var ctx: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    @WithUserDetails("admin")
    fun 공지사항_입력(){

        val title: String = "제목"

        val noticeSaveDTO = NoticeSaveDTO(title, "내용입니다.~")
        val toJson = Gson().toJson(noticeSaveDTO)
        log.debug("toJson : $toJson")

        mockMvc.perform(post("/api/notice/").contentType(MediaType.APPLICATION_JSON).content(toJson))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.subject").value(title))
            .andDo(print())
    }

}