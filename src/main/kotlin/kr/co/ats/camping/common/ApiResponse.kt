package kr.co.ats.camping.common

data class ApiResponse(
    val success: Boolean,
    val code: String?=null,
    val message: String? = null,
    val data: Any? = null
) {

    companion object{
        fun ok(data:Any?=null)=ApiResponse(true,data=data,code="SUC")
        fun okMessage(data:Any?=null,message: String?=null)=ApiResponse(true,data=data,message=message,code="SUC")
        fun error(message: String? = null,code:String?=null) = ApiResponse(false, message=message,code=code)
    }
}