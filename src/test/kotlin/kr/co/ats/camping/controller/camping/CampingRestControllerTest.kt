package kr.co.ats.camping.controller.camping

import com.google.gson.Gson
import kr.co.ats.camping.code.CodeYn
import kr.co.ats.camping.code.Scale
import kr.co.ats.camping.code.Season
import kr.co.ats.camping.dto.camping.CampingReviewUpdateDTO
import kr.co.ats.camping.dto.camping.CampingUpdateDTO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.io.FileInputStream
import javax.transaction.Transactional


@Transactional
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class CampingRestControllerTest{

    lateinit var mockMvc: MockMvc

    @set:Autowired
    lateinit var ctx: WebApplicationContext

    @BeforeEach
    fun setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply { SecurityMockMvcConfigurers.springSecurity() }
            .build()
    }

    @Test
    @DisplayName("캠핑장 등록")
    @WithUserDetails("admin")
    fun campingRegister(){

        val file = ClassPathResource("testTemplate/noticeTestFile.xlsx").file
        val uploadFile = FileInputStream(file)

        val multipartFile = MockMultipartFile("uploadFileList", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)

        val campingName = "호매실 캠핑장"

        val info: MultiValueMap<String, String> = LinkedMultiValueMap()

        info.add("campingName", campingName)
        info.add("scale", Scale.SMALL.name)
        info.add("address", "경기도 수원시 권선구")
        info.add("addressDetail", "호매실로 165번길 30")
        info.add("latitude", "37.258291629904804")
        info.add("longitude","126.96263239178766")
        info.add("autoYn", CodeYn.Y.name)
        info.add("content","설명이 들어갈 예정입니다.")
        info.add("price","50000")


        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/camping")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.campingName").value(campingName))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("캠핑장 등록_파일_없음")
    @WithUserDetails("admin")
    fun campingRegister_noFile() {

//        val file = ClassPathResource("testTemplate/noticeTestFile.xlsx").file
//        val uploadFile = FileInputStream(file)

//        val multipartFile = MockMultipartFile("uploadFile", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)

        val campingName = "호매실아아 캠핑장"

        val info: MultiValueMap<String, String> = LinkedMultiValueMap()

        info.add("campingName", campingName)
        info.add("scale", Scale.SMALL.name)
        info.add("address", "경기도 수원시 권선구")
        info.add("addressDetail", "호매실로 165번길 30")
        info.add("latitude", "37.258291629904804")
        info.add("longitude", "126.96263239178766")
        info.add("autoYn", CodeYn.Y.name)
        info.add("content", "설명이 들어갈 예정입니다.")
        info.add("price", "50000")


        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/camping")
//                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.campingName").value(campingName))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("캠핑장 목록 조회")
    @WithUserDetails("admin")
    fun campingSearch(){
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/camping")
                .queryParam("searchKeyWord","수원")
                .queryParam("searchStartRating","0")
                .queryParam("searchEndRating","10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(0))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("캠핑장 상세 조회")
    @WithUserDetails("admin")
    fun campingDetail(){
        val campingInfoKey :Long = 21

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/camping/$campingInfoKey")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.campingInfoKey").value(campingInfoKey))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("캠핑 비슷한 이름")
    @WithUserDetails("admin")
    fun findLikeName(){
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/camping/likeName")
                .queryParam("name", "수원")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("캠핑장 정보 수정")
    @WithUserDetails("taeho")
    fun campingUpdate(){
        val compingInfoKey = 21L
        val campingUpdateDTO = CampingUpdateDTO("수정이름",Scale.MEDIUM,"서울특별시 용산구 만리재로40길 15","1층 ","37.5545238844734","126.9681744641918","내용 변경합니다.",50000,CodeYn.N)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/camping/"+ compingInfoKey)
                .content(Gson().toJson(campingUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho")
    fun `캠핑장 정보 사진 추가`(){
        val file = ClassPathResource("testTemplate/noticeTestFile.xlsx").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("uploadFile", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.add("campingInfoKey", "21")
        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/camping/fileAppend")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.campingDetailFileKey").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho")
    fun `캠핑장 사진 삭제`(){
        val campingDetailFileKey:Long = 41
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/camping/file/"+ campingDetailFileKey)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @WithUserDetails("taeho")
    fun `캠핑장 정보 삭제_내가_쓴글_아님`(){
        val campingInfoKey:Long = 65
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/camping/$campingInfoKey")
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @WithUserDetails("taeho_user")
    fun `캠핑장 정보 삭제_내가_쓴글`() {
        val campingInfoKey: Long = 21
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/camping/$campingInfoKey")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho_user")
    fun `캠핑장_리뷰_등록`() {
        val campingInfoKey = 65L

        val info: MultiValueMap<String, String> = LinkedMultiValueMap()

        info.add("rating", "10")
        info.add("review", "꼬짐 아 몰랑 꼬짐")
        info.add("season", Season.SPRING.name)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/camping/$campingInfoKey")
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.campingReviewKey").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho_user")
    fun `캠핑장 리뷰_등록 사진 추가`() {
        val file = ClassPathResource("testTemplate/testImg.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("uploadFileList", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val campingInfoKey = 65L
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.add("rating", "10")
        info.add("review", "꼬짐 아 몰랑 꼬짐")
        info.add("season", Season.SPRING.name)
        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/camping/${campingInfoKey}")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.fileList[0].campingReviewFileKey").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho_user")
    fun `후기_사진_추가`(){
        val file = ClassPathResource("testTemplate/testImg.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("uploadFile", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val campingInfoKey = 21L
        val campingReviewKey = 7L
        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/camping/$campingInfoKey/$campingReviewKey")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho_user")
    fun `후기_사진_삭제`(){
        val campingInfoKey = 21
        val campingReviewKey = 7
        val campingReviewFileKey = 3

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/camping/$campingInfoKey/$campingReviewKey/$campingReviewFileKey"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("admin")
    fun `후기_삭제`(){
        val campingInfoKey = 21
        val campingReviewKey = 7

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/camping/$campingInfoKey/$campingReviewKey"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithUserDetails("taeho_user")
    fun `후기_수정`(){
        val campingInfoKey  = 21
        val campingReviewKey = 7

        val campingReviewUpdateDTO = CampingReviewUpdateDTO(10,"아몰랑!!", Season.SPRING)
        val toJson = Gson().toJson(campingReviewUpdateDTO)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/camping/$campingInfoKey/$campingReviewKey")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())


    }

}