package kr.co.ats.camping.service.camping

import kr.co.ats.camping.code.Path
import kr.co.ats.camping.code.Role
import kr.co.ats.camping.config.exception.CampingATSException
import kr.co.ats.camping.dto.authUser.AuthUserDTO
import kr.co.ats.camping.dto.camping.*
import kr.co.ats.camping.dto.common.FileDTO
import kr.co.ats.camping.entity.camping.*
import kr.co.ats.camping.repository.camping.*
import kr.co.ats.camping.utils.delete
import kr.co.ats.camping.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils

@Service
class CampingService {

    private val log = LoggerFactory.getLogger(CampingService::class.java)

    @Value("\${file.upload.path}")
    lateinit var root: String

    @set:Autowired
    lateinit var campingContentHisRepository: CampingContentHisRepository

    @set:Autowired
    lateinit var campingContentRepository: CampingContentRepository

    @set:Autowired
    lateinit var campingDetailFileRepository:CampingDetailFileRepository

    @set:Autowired
    lateinit var campingDetailRepository: CampingDetailRepository

    @set:Autowired
    lateinit var campingDetailHisRepository: CampingDetailHisRepository

    @set:Autowired
    lateinit var campingInfoRepository:CampingInfoRepository

    @set:Autowired
    lateinit var campingReviewRepository:CampingReviewRepository

    @set:Autowired
    lateinit var campingReviewFileRepository:CampingReviewFileRepository

    @Transactional
    fun campingSave(campingSaveDTO: CampingSaveDTO): CampingResultDTO {
        val campingInfo = campingInfoRepository.save(CampingInfo())
        val campingContent = campingContentRepository.save(CampingContent(campingSaveDTO.content, campingSaveDTO.price, campingInfo))
        val campingDetail = campingDetailRepository.save(CampingDetail(campingSaveDTO.campingName, campingSaveDTO.scale.name, campingSaveDTO.address, campingSaveDTO.addressDetail, campingSaveDTO.latitude, campingSaveDTO.longitude,campingSaveDTO.autoYn.name, campingInfo, null))
        // ????????? ????????? ?????? ?????? ?????????
        val fileResultList = mutableListOf<CampingDetailFileResultDTO>()

        campingSaveDTO.uploadFileList?.forEach{multipartFile ->
            val fileDTO: FileDTO = multipartFile.save(Path.CAMPING.filePath, root)
            val saveFile = campingDetailFileRepository.save(CampingDetailFile(fileDTO.fileName, fileDTO.filePath, fileDTO.fileSize ?: 0, campingDetail))
            fileResultList.add(CampingDetailFileResultDTO(saveFile))
        }

        return CampingResultDTO(campingInfo,campingContent, campingDetail, fileResultList)
    }

    fun campingSearch():List<CampingInfo>{
        return campingInfoRepository.findCamping()
    }

    fun findByPage(campingSearchDTO: CampingSearchDTO): Page<CampingPageResultDTO>? {
        val pageRequest = PageRequest.of(campingSearchDTO.currentPage, campingSearchDTO.pageSize, Sort.by("campingInfoKey").descending())
        return campingInfoRepository.findByPage(campingSearchDTO, pageRequest)?.map { CampingPageResultDTO(it) }

    }

    fun findDetail(campingInfoKey: Long) : CampingDetailResultDTO{
        val campingInfo:CampingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow {
            throw CampingATSException("CAMPING.NOT_FOUND")
        }
        return CampingDetailResultDTO(campingInfo)
    }


    fun findLikeName(name: String) : List<CampingLikeNameResultDTO>? {
        val replace = name.replace("?????????", "")
        return if (replace.isNotBlank()) {
            campingDetailRepository.findByCampingNameContains(replace).map { CampingLikeNameResultDTO(it) }
        }else{
            null
        }
    }

     /**
     * ????????? ?????? ??????
     */
    @Transactional
    fun update(campingInfoKey: Long, campingUpdateDTO: CampingUpdateDTO) : CampingResultDTO {
         val campingInfo: CampingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }

         val campingContent = campingInfo.campingContent
         val cnt = campingContentHisRepository.countByCampingContentEquals(campingContent!!)
         campingContentHisRepository.save(CampingContentHis(cnt + 1, campingContent.content, campingContent.price, campingContent))


         val campingDetail = campingInfo.campingDetail
         val count = campingDetailHisRepository.countByCampingDetail(campingDetail!!)
         campingDetailHisRepository.save(
             CampingDetailHis(
                 count + 1,
                 campingDetail.campingName,
                 campingDetail.scale,
                 campingDetail.address,
                 campingDetail.addressDetail,
                 campingDetail.latitude,
                 campingDetail.longitude,
                 campingDetail.autoYn,
                 campingDetail
             ))

         campingContent.update(campingUpdateDTO)
         campingDetail.update(campingUpdateDTO)
         return CampingResultDTO(campingInfoRepository.save(campingInfo))

     }

    /**
     * ????????? ?????? ??????
     */
    fun campingDetailFileAppend(campingFileUpdateDTO: CampingFileUpdateDTO) : CampingDetailFileResultDTO? {
        val campingInfo = campingInfoRepository.findById(campingFileUpdateDTO.campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }
        val campingDetail = campingInfo.campingDetail
        return if (!ObjectUtils.isEmpty(campingFileUpdateDTO.uploadFile)) {
            val fileDTO: FileDTO = campingFileUpdateDTO.uploadFile.save(Path.CAMPING.filePath, root)
            val saveFile = campingDetailFileRepository.save(CampingDetailFile(fileDTO.fileName, fileDTO.filePath, fileDTO.fileSize ?: 0, campingDetail!!))
            CampingDetailFileResultDTO(saveFile)
        }else{
            null
        }
    }

    /**
     * ????????? ?????? ??????
     */
    fun campingDetailFileDelete(campingDetailFileKey: Long, authUserDTO: AuthUserDTO) {
        val campingDetailFile:CampingDetailFile = campingDetailFileRepository.findById(campingDetailFileKey).orElseThrow { throw CampingATSException("CAMPING.FILE.NOT_FOUND") }
        if (campingDetailFile.regId == authUserDTO.memberId || authUserDTO.role == Role.ROLE_ADMIN.name) {
            campingDetailFile.delete(root)
            campingDetailFileRepository.delete(campingDetailFile)
        }else{
            throw CampingATSException("CAMPING.NOT_FILE_DELETE")
        }

    }

    /**
     * ????????? ??????
     */
    @Transactional
    fun deleteCampingInfo(campingInfoKey: Long, authUserDTO: AuthUserDTO) {
        val campingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }
        if (authUserDTO.role!= Role.ROLE_ADMIN.name && campingInfo.regId != authUserDTO.memberId) {
            throw CampingATSException("CAMPING.NOT_DELETE")
        }else{
            // ?????? ?????? ?????????
            // ?????? ????????? ??????
            val campingDetailFileList = campingInfo.campingDetail?.campingDetailFileList
            campingDetailFileList?.forEach { campingDetailFile ->
                campingDetailFile.delete(root)
            }
            // ?????? ?????? ??????
            if (!ObjectUtils.isEmpty(campingDetailFileList)) {
                campingDetailFileRepository.deleteAll(campingDetailFileList!!)
            }
            // ????????? ??????
            campingInfo.campingDetail?.also { campingDetailRepository.delete(it) }

            // ????????? ??????
            campingInfo.campingContent?.also { campingContentRepository.delete(it) }

            // ?????? ??????
            campingInfo.campingReviewList?.also {campingReviewRepository.deleteAll(it)}

            // ?????? ??????
            campingInfoRepository.delete(campingInfo)
        }
    }

    /**
     * ?????? ??????
     */
    fun saveReview(campingInfoKey: Long, campingReviewSaveDTO: CampingReviewSaveDTO, authUserDTO: AuthUserDTO): CampingReviewResultDTO {
        val campingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }

        val myReviewCount = campingReviewRepository.countBySeasonAndRegIdAndCampingInfo(campingReviewSaveDTO.season.name, authUserDTO.memberId, campingInfo)

        return if(myReviewCount > 0){
            throw CampingATSException("CAMPING.DUPLICATE")
        }else{
            val campingReview = campingReviewRepository.save(CampingReview(campingReviewSaveDTO.rating, campingReviewSaveDTO.review, campingReviewSaveDTO.season.name, campingInfo, null))
            val fileResultList = mutableListOf<CampingReviewFileResultDTO>()
            campingReviewSaveDTO.uploadFileList?.forEach { multipartFile ->
                val fileDTO: FileDTO = multipartFile.save(Path.REVIEW.filePath, root)
                val saveFile = campingReviewFileRepository.save(CampingReviewFile(fileDTO.fileName, fileDTO.filePath, fileDTO.fileSize ?: 0, campingReview))
                fileResultList.add(CampingReviewFileResultDTO(saveFile))
            }
            // ?????? ?????? ??????
            CampingReviewResultDTO(campingReview, fileResultList)
        }
    }

    /**
     * ?????? ?????? ??????
     */
    fun campingReviewUpdate(campingInfoKey: Long, campingReviewKey: Long, campingReviewUpdateDTO: CampingReviewUpdateDTO, authUserDTO: AuthUserDTO): CampingReviewResultDTO {
        val campingReview = campingReviewRepository.findByCampingReviewKeyAndCampingInfo_CampingInfoKey(campingReviewKey, campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND_REVIEW") }
        if (authUserDTO.memberId == campingReview.regId) {
            // ?????? ??????
            // ???????????? ?????? ??????
            // ???????????? ?????? ?????? ??????
            // ?????? ????????? ????????? ?????? ????????? ????????? ??????
            return if (campingReview.season == campingReviewUpdateDTO.season.name) {
                // ?????? ???????????? ??????
                campingReview.rating = campingReviewUpdateDTO.rating
                campingReview.review = campingReviewUpdateDTO.review
                val save = campingReviewRepository.save(campingReview)
                CampingReviewResultDTO(save)

            }else{
                //?????? ???????????????
                val myReviewCount = campingReviewRepository.countBySeasonAndRegIdAndCampingInfo(campingReviewUpdateDTO.season.name, authUserDTO.memberId, campingReview.campingInfo)
                if(myReviewCount > 0){
                    throw CampingATSException("CAMPING.DUPLICATE")
                }else{
                    campingReview.rating = campingReviewUpdateDTO.rating
                    campingReview.review = campingReviewUpdateDTO.review
                    campingReview.season = campingReviewUpdateDTO.season.name
                    val save = campingReviewRepository.save(campingReview)
                    CampingReviewResultDTO(save)
                }
            }
        }else{
            throw CampingATSException("CAMPING.NOT_UPDATE")
        }


    }

    /**
     * ?????? ?????? ??????
     */
    fun appendReviewPhoto(campingInfoKey: Long, campingReviewKey: Long, campingFileUpdateDTO: CampingFileUpdateDTO, authUserDTO: AuthUserDTO) : CampingReviewFileResultDTO{
        val campingReview = campingReviewRepository.findByCampingReviewKeyAndRegIdAndCampingInfo_CampingInfoKey(campingReviewKey, authUserDTO.memberId, campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND_REVIEW") }
        val fileDTO: FileDTO = campingFileUpdateDTO.uploadFile.save(Path.REVIEW.filePath, root)
        return CampingReviewFileResultDTO(campingReviewFileRepository.save(CampingReviewFile(fileDTO.fileName, fileDTO.filePath, fileDTO.fileSize ?: 0, campingReview)))

    }

    /**
     * ?????? ?????? ??????
     */
    fun deleteReviewPhoto(campingInfoKey: Long, campingReviewKey: Long, campingReviewFileKey: Long, authUserDTO: AuthUserDTO) {
        val campingReviewFile = campingReviewFileRepository.findByCampingReviewFileKeyAndCampingReview_CampingReviewKeyAndCampingReview_CampingInfo_CampingInfoKey(campingReviewFileKey, campingReviewKey, campingInfoKey)
            .orElseThrow { throw CampingATSException("CAMPING.FILE.NOT_FOUND") }

        if (authUserDTO.memberId == campingReviewFile.regId || authUserDTO.role == Role.ROLE_ADMIN.name) {
            // ?????? ??????
            campingReviewFile.delete(root)
            campingReviewFileRepository.delete(campingReviewFile)
        } else {
            throw CampingATSException("CAMPING.NOT_FILE_DELETE")
        }
    }

    /**
     * ?????? ?????? ??????
     */
    fun deleteReview(campingInfoKey: Long, campingReviewKey: Long, authUserDTO: AuthUserDTO) {
        val campingReview = campingReviewRepository.findByCampingReviewKeyAndCampingInfo_CampingInfoKey(campingReviewKey, campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND_REVIEW") }
        if (campingReview.regId == authUserDTO.memberId || Role.ROLE_ADMIN.name == authUserDTO.role) {
            // ??????
            // ?????? ??????
            campingReview.campingReviewList?.forEach {
                it.delete(root)
                campingReviewFileRepository.delete(it)
            }
            campingReviewRepository.delete(campingReview)
            //
        }else {
            throw CampingATSException("CAMPING.NOT_DELETE")
        }
    }

    /**
     * ?????? ?????? ?????? ??????
     */
    fun updateAvgReview(campingInfoKey:Long){
        val campingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }
        val average = campingInfo.campingReviewList?.map { campingReview -> campingReview.rating }?.average()
        campingInfo.avgRating = (average?:0) as Double
        campingInfoRepository.save(campingInfo)
    }


}