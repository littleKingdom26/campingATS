package kr.co.ats.camping.common

import io.swagger.annotations.ApiModelProperty
import kr.co.ats.camping.code.ResultCode

open class ApiResponse(
    @ApiModelProperty(value="성공여부",example = "true")
    val success: Boolean,
    @ApiModelProperty(value = "성공여부가 false 일때 CODE",example = "SUCCESS")
    val code: String?=null,
    @ApiModelProperty(value = "성공여부가 false 일때 메시지")
    val message: String? = null,
    @ApiModelProperty(value = "결과")
    val data: Any? = null
) {
        companion object{
        fun ok(data:Any?=null)=ApiResponse(true,data=data,code=ResultCode.SUCCESS.name)
        fun okMessage(data:Any?=null,message: String?=null)=ApiResponse(true,data=data,message=message,code= ResultCode.SUCCESS.name)
        fun error(message: String? = null, code:String?= ResultCode.FAIL.name) = ApiResponse(false, message=message,code=code)
    }
}