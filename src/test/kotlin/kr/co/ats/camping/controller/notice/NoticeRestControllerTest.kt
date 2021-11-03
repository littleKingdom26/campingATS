package kr.co.ats.camping.controller.notice


import com.google.gson.Gson
import kr.co.ats.camping.dto.notice.NoticeUpdateDTO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.io.FileInputStream
import javax.transaction.Transactional


@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class NoticeRestControllerTest {

    private val log = LoggerFactory.getLogger(NoticeRestControllerTest::class.java)

    lateinit var mockMvc: MockMvc

    @set:Autowired
    lateinit var ctx: WebApplicationContext

    @BeforeEach
    fun setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply { springSecurity() }
            .build()
    }

    @Test
    @WithUserDetails("admin")
    fun notice_save() {


        val title = "제목"
        /*


        val noticeSaveDTO = NoticeSaveDTO(title, "내용입니다.~")
        val toJson = Gson().toJson(noticeSaveDTO)
        log.debug("toJson : $toJson")
        mockMvc
            .perform(
                post("/api/notice")
                    .contentType(MediaType.APPLICATION_JSON).content(toJson)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.subject").value(title))
            .andDo(print())

         */
        val file = ClassPathResource("testTemplate/noticeTestFile.xlsx").file
        val uploadFile = FileInputStream(file)

        val multipartFile = MockMultipartFile("uploadFile", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)


        mockMvc.perform(
            multipart("/api/notice")
                .file(multipartFile)
                .param("subject",title)
                .param("content","내용입니다.")
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.subject").value(title))
            .andDo(print())

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("공지사항 상세 ")
    fun notice_detail(){
        /*32*/
        val noticeKey:Long = 66
        mockMvc
            .perform(
                get("/api/notice/detail/$noticeKey")
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.subject").value("sdf33"))
            .andExpect(jsonPath("$.data.fileList[0].fileName").value("20211030140704576600123930.jpg"))
            .andDo(print())

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("공지사항 상세 건수 없음")
    fun notice_detail_isNull() {
        /*32*/
        val noticeKey: Long = 1
        mockMvc
            .perform(
                get("/api/notice/detail/$noticeKey")
            )
            .andExpect(status().is4xxClientError)
            .andDo(print())

    }

    @Test
    @WithUserDetails("taeho")
    @DisplayName("공지사항 목록 조회")
    fun notice_page(){
        mockMvc.perform(get("/api/notice"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value("true"))
            .andExpect(jsonPath("$.data.size").value(20))
            .andDo(print())
    }

    @Test
    @Transactional
    @WithUserDetails("taeho")
    @DisplayName("공지사항 수정")
    fun notice_update(){
        val noticeKey:Long = 65
        val title = "공지사항 수정 합니다."
        val noticeupdate = NoticeUpdateDTO(title, "내용입니다.~")
        val toJson = Gson().toJson(noticeupdate)

        mockMvc.perform(put("/api/notice/$noticeKey").contentType(MediaType.APPLICATION_JSON).content(toJson))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.subject").value(title))
            .andDo(print())
    }

    @Test
    @Transactional
    @WithUserDetails("taeho")
    @DisplayName("공지사항 수정_실패")
    fun notice_update_fail() {
        val noticeKey: Long = 1
        val title = "공지사항 수정 합니다."
        val noticeupdate = NoticeUpdateDTO(title, "내용입니다.~")
        val toJson = Gson().toJson(noticeupdate)

        mockMvc.perform(put("/api/notice/$noticeKey").contentType(MediaType.APPLICATION_JSON).content(toJson))
            .andExpect(status().is4xxClientError)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code").value("NOTICE.NOT_FOUND"))
            .andDo(print())
    }


    @Test
    @Transactional
    @WithUserDetails("taeho")
    @DisplayName("공지사항 삭제")
    fun notice_delete() {
        val noticeKey: Long = 65
        mockMvc.perform(delete("/api/notice/$noticeKey").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }
}

/*

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class NoticeRestControllerTest {
    private val log = LoggerFactory.getLogger(NoticeRestControllerTest::class.java)


    lateinit var mockMvc: MockMvc

    @set:Autowired
    lateinit var ctx: WebApplicationContext

    val USER_KEY:String= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoIiwiaXNzIjoiQVRTIiwiZXhwIjoxNjM3OTQwNzk3LCJpYXQiOjE2MzUzNDg3OTcsIm1lbWJlcklkIjoidGFlaG9fdXNlciJ9.Mirbxc3hC-4UWmQccbCV0yIj9XYnGWzoEN_3caxWWRHqEptnt6aNSCjhrCUt3imRpbL9E-W26F0cDMKeHEUgGw"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply { springSecurity() }
            .build()
    }

    @Test
    fun notice_save() {

        val title = "제목"

        val noticeSaveDTO = NoticeSaveDTO(title, "내용입니다.~")
        val toJson = Gson().toJson(noticeSaveDTO)
        log.debug("toJson : $toJson")

        val headers = HttpHeaders()
        headers.add("Authorization", USER_KEY)


        mockMvc
            .perform(
                post("/api/notice").headers(headers)
                    .contentType(MediaType.APPLICATION_JSON).content(toJson)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.subject").value(title))
            .andDo(print())
    }


}
*/
