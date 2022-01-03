package kr.co.ats.camping.service.camping

import kr.co.ats.camping.code.Path
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
        // 파일이 있으면 파일 저장 필요함
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
        val replace = name.replace("캠핑장", "")
        return if (replace.isNotBlank()) {
            campingDetailRepository.findByCampingNameContains(replace).map { CampingLikeNameResultDTO(it) }
        }else{
            null
        }
    }

     /**
     * 캠핑장 정보 수정
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
     * 캠핑장 파일 추가
     */
    fun campingDetailFileAppend(campingFileUpdateDTO: CampingFileUpdateDTO) : CampingDetailFileResultDTO? {
        val campingInfo = campingInfoRepository.findById(campingFileUpdateDTO.campingInfoSeq).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }
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
     * 캠핑장 파일 삭제
     */
    fun campingDetailFileDelete(campingDetailFileKey: Long) {
        val campingDetailFile:CampingDetailFile = campingDetailFileRepository.findById(campingDetailFileKey).orElseThrow { throw CampingATSException("CAMPING.FILE.NOT_FOUND") }
        campingDetailFile.delete(root)
        campingDetailFileRepository.delete(campingDetailFile)
    }

    fun deleteCampingInfo(campingInfoKey: Long, authUserDTO: AuthUserDTO) {
        val campingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }
        if (campingInfo.regId != authUserDTO.memberId) {
            log.debug("${campingInfo.regId}  , ${authUserDTO.memberId}")
            throw CampingATSException("CAMPING.NOT_DELETE")
        }else{
            // 삭제 하러 갑시다
            // 파일 물리적 삭제
            log.debug("파일 물리 삭제")
            val campingDetailFileList = campingInfo.campingDetail?.campingDetailFileList
            campingDetailFileList?.forEach { campingDetailFile ->
                campingDetailFile.delete(root)
            }
            // 파일 디비 삭제
            log.debug("파일 디비 삭제")
            if (!ObjectUtils.isEmpty(campingDetailFileList)) {
                campingDetailFileRepository.deleteAll(campingDetailFileList!!)
            }
            // 디테일 삭제
            log.debug("디테일 삭제")
            campingInfo.campingDetail?.also { campingDetailRepository.delete(it) }

            // 컨텐츠 삭제
            log.debug("컨텐츠 삭제")
            campingInfo.campingContent?.also { campingContentRepository.delete(it) }

            // 인포 삭제
            log.debug("인포 삭제")
            campingInfoRepository.delete(campingInfo)
        }
    }

    /**
     * 리뷰 등록
     */
    fun reviewSave(campingInfoKey: Long, campingReviewSaveDTO: CampingReviewSaveDTO, authUserDTO: AuthUserDTO): CampingReviewResultDTO {
        val campingInfo = campingInfoRepository.findById(campingInfoKey).orElseThrow { throw CampingATSException("CAMPING.NOT_FOUND") }
        val myReviewCount = campingReviewRepository.countBySeasonAndRegIdAndCampingInfo(campingReviewSaveDTO.season.name, authUserDTO.memberId, campingInfo)
        return when (myReviewCount > 0) {
            true -> {
                throw CampingATSException("CAMPING.DUPLICATE")
            }
            false -> {
                // 파일 물리 저장
                return CampingReviewResultDTO(campingReviewRepository.save(CampingReview(campingReviewSaveDTO.rating, campingReviewSaveDTO.review, campingReviewSaveDTO.season.name, campingInfo, null)))


//                campingReviewSaveDTO.uploadFileList?.isNotEmpty().let { campingReviewSaveDTO -> }
//                CampingReview()
            }
        }



    }


}