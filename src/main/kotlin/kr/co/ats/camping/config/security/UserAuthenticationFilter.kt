package kr.co.ats.camping.config.security

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationFilter(authenticationManager: AuthenticationManager): UsernamePasswordAuthenticationFilter(authenticationManager) {

    private val log = LoggerFactory.getLogger(UserAuthenticationFilter::class.java)

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        var userName:String?=""
        var password:String?=""

        val header = request.getHeader("Content-Type")
        if (MediaType.APPLICATION_JSON_VALUE.equals(header)) {
            val readLines: List<String> = request.reader.readLines()
            val flatMap: String = readLines.joinToString("")
            val jsonObject: JsonObject = JsonParser.parseString(flatMap).asJsonObject
            userName = jsonObject.get("userName").asString
            password = jsonObject.get("password").asString
        }else{
            userName = obtainUsername(request)
            password = obtainPassword(request)
        }
        val token:UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userName,password)

        return authenticationManager.authenticate(token)
    }
}