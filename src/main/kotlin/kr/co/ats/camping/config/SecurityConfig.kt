package kr.co.ats.camping.config

import kr.co.ats.camping.config.jwt.JwtAuthenticationEntryPoint
import kr.co.ats.camping.config.jwt.JwtAuthenticationFilter
import kr.co.ats.camping.config.security.UserAuthenticationFailureHandler
import kr.co.ats.camping.config.security.UserAuthenticationFilter
import kr.co.ats.camping.service.member.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val memberService: MemberService
) : WebSecurityConfigurerAdapter(){


    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
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


    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/login","/api/join").permitAll()
            .anyRequest().authenticated()
            .and().cors()
            .and().addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter::class.java)
    }

    /**
     * 인증 필터
     */
    @Bean
    fun userAuthenticationFilter():UserAuthenticationFilter{
        var filter = UserAuthenticationFilter(authenticationManagerBean())
//        filter.setAuthenticationSuccessHandler()
        filter.setAuthenticationFailureHandler(userFailureHandler())
        return filter
    }

    fun userFailureHandler():AuthenticationFailureHandler{
        return UserAuthenticationFailureHandler()
    }


}
