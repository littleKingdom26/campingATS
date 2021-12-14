package kr.co.ats.camping.service.camping

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class CampingServiceTest{
    @Autowired
    private lateinit var campingService:CampingService

    @Test
    fun campingSearch(){
        val campingSearch = campingService.campingSearch()
        for (campingInfo in campingSearch) {
            println(campingInfo.campingInfoKey)
        }
    }

}