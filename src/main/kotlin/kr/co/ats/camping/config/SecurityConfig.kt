package kr.co.ats.camping.config

import kr.co.ats.camping.config.jwt.JwtAuthenticationEntryPoint
import kr.co.ats.camping.config.jwt.JwtAuthenticationFilter
import kr.co.ats.camping.config.security.UserAuthenticationFailureHandler
import kr.co.ats.camping.config.security.UserAuthenticationFilter
import kr.co.ats.camping.config.security.UserAuthenticationSuccessHandler
import kr.co.ats.camping.service.member.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

@Configurable
@EnableWebSecurity
class SecurityConfig(
    private val memberService: MemberService
) : WebSecurityConfigurerAdapter(){


    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    fun configureAuthentication(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(this.memberService)
            .passwordEncoder(this.passwordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/swagger-ui/**","/swagger-resources/**","/v2/**")
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/login","/api/join").permitAll()
            .anyRequest().authenticated()
            .and().cors()
            .and().addFilter(JwtAuthenticationFilter(authenticationManagerBean(),memberService))
            .addFilter(userAuthenticationFilter())
            .exceptionHandling().authenticationEntryPoint(JwtAuthenticationEntryPoint())
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

    /**
     * 인증 필터
     */
    @Bean
    fun userAuthenticationFilter():UserAuthenticationFilter{
        var filter = UserAuthenticationFilter(authenticationManagerBean())
        filter.setAuthenticationSuccessHandler(userSuccessHandler())
        filter.setAuthenticationFailureHandler(userFailureHandler())
        return filter
    }

    fun userFailureHandler():AuthenticationFailureHandler{
        return UserAuthenticationFailureHandler()
    }

    fun userSuccessHandler():AuthenticationSuccessHandler{
        return UserAuthenticationSuccessHandler()
    }


}
