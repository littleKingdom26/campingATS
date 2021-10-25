package kr.co.ats.camping.controller.join


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import javax.transaction.Transactional


/** TestRestTemplate test 방법 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SignupRestControllerTest(@Autowired val restTemplate: TestRestTemplate){

    private val log = LoggerFactory.getLogger(SignupRestControllerTest::class.java)
    @Test
    fun check_id(){

        val entity = restTemplate.getForEntity<String>("/api/signUp/checkId?checkId=taeho")
        log.debug("entity : $entity")
        assertThat(entity.statusCode)
            .isEqualTo(HttpStatus.BAD_REQUEST)
    }

}

/**  mock test 방법 */
/*
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class SignupRestControllerTest {

    @set:Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun check_id(){
        mockMvc.perform(MockMvcRequestBuilders.get("/api/signUp/checkId?checkId=taeho"))
            .andExpect(status().is4xxClientError)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())


    }


}

 */