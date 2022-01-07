package kr.co.ats.camping.dto.camping


import kr.co.ats.camping.entity.camping.CampingReviewFile

data class CampingReviewFileResultDTO(
    var campingReviewFileKey: Long?,
    var fileName: String,
    var fileSize: Long,
    var filePath: String,
    var imageViewer: String
) {
    constructor(campingReviewFile: CampingReviewFile) : this(
        campingReviewFileKey = campingReviewFile.campingReviewFileKey,
        fileName = campingReviewFile.fileName,
        fileSize = campingReviewFile.fileSize,
        filePath = campingReviewFile.filePath,
        imageViewer = "/imageView/${campingReviewFile.filePath}/${campingReviewFile.fileName}"

    )
}
