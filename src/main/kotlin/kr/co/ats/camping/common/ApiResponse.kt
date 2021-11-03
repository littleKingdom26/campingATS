package kr.co.ats.camping.common

import io.swagger.annotations.ApiModelProperty

open class ApiResponse(
    @ApiModelProperty(value="성공여부",example = "true")
    val success: Boolean,
    @ApiModelProperty(value = "성공여부가 false 일때 CODE",example = "SUC")
    val code: String?=null,
    @ApiModelProperty(value = "성공여부가 false 일때 메시지")
    val message: String? = null,
    @ApiModelProperty(value = "결과")
    val data: Any? = null
) {
        companion object{
        fun ok(data:Any?=null)=ApiResponse(true,data=data,code="SUC")
        fun okMessage(data:Any?=null,message: String?=null)=ApiResponse(true,data=data,message=message,code="SUC")
        fun error(message: String? = null,code:String?=null) = ApiResponse(false, message=message,code=code)
    }
}