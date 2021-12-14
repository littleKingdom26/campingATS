package kr.co.ats.camping.service.camping

import kr.co.ats.camping.code.Path
import kr.co.ats.camping.dto.camping.CampingDetailFileResultDTO
import kr.co.ats.camping.dto.camping.CampingResultDTO
import kr.co.ats.camping.dto.camping.CampingSaveDTO
import kr.co.ats.camping.dto.common.FileDTO
import kr.co.ats.camping.entity.camping.CampingContent
import kr.co.ats.camping.entity.camping.CampingDetail
import kr.co.ats.camping.entity.camping.CampingDetailFile
import kr.co.ats.camping.entity.camping.CampingInfo
import kr.co.ats.camping.repository.camping.*
import kr.co.ats.camping.utils.save
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CampingService {

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
    lateinit var campingInfoRepository:CampingInfoRepository


    @Transactional
    fun campingSave(campingSaveDTO: CampingSaveDTO): CampingResultDTO {
        val campingInfo = campingInfoRepository.save(CampingInfo())
        val campingContent = campingContentRepository.save(CampingContent(campingSaveDTO.content, campingSaveDTO.price, campingInfo))
        val campingDetail = campingDetailRepository.save(CampingDetail(campingSaveDTO.campingName, campingSaveDTO.scale.name, campingSaveDTO.address, campingSaveDTO.addressDetail, campingSaveDTO.latitude, campingSaveDTO.longitude, campingInfo, null))
        // 파일이 있으면 파일 저장 필요함
        val fileResultList = mutableListOf<CampingDetailFileResultDTO>()
        if (campingSaveDTO.uploadFile != null) {
            for (multipartFile in campingSaveDTO.uploadFile!!) {
                // 파일 저장
                val fileDTO: FileDTO = multipartFile.save(Path.CAMPING.filePath, root)
                val saveFile = campingDetailFileRepository.save(CampingDetailFile(fileDTO.fileName, fileDTO.filePath, fileDTO.fileSize ?: 0, campingDetail))
                fileResultList.add(CampingDetailFileResultDTO(saveFile))
            }
        }
        return CampingResultDTO(campingContent, campingDetail, fileResultList)
    }

    fun campingSearch():List<CampingInfo>{
        return campingInfoRepository.findCamping()
    }
}