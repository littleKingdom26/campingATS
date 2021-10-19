package kr.co.ats.camping.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class swaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로
            // 할당된 모든 URL 리스트를 추출
            .paths(PathSelectors.ant("/api/**")) // 그중 /api/** 인 URL들만 필터링
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder().title("이 문서는 campingATS Api 문서 입니다.")
            .description("Created by xxATS").license("Apache License Version 2.0")
            .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("1.0").build()
    }
}