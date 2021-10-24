package kr.co.ats.camping.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket


@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket {


        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로
            // 할당된 모든 URL 리스트를 추출
            .paths(PathSelectors.ant("/api/**")) // 그중 /api/** 인 URL들만 필터링

            .build()
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder().title("이 문서는 campingATS Api 문서 입니다.")
            .description("Created by xxATS").license("Apache License Version 2.0")
            .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("1.0").build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }

}