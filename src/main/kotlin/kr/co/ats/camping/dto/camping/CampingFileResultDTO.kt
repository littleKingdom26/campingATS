package kr.co.ats.camping.dto.camping

import kr.co.ats.camping.entity.camping.CampingDetailFile

data class CampingFileResultDTO (val fileName:String) {
    constructor(campingDetailFile: CampingDetailFile) : this(campingDetailFile.fileName)
}