package com.lottolike.jaery.lotto.lotto.domain

/**
 * Lotto 사이트에서 파싱해서 저장할 변수
 * @param round : Int  ex) 976
 * @param numbers : String ex) 1,2,3,4,5,6+7
 * @param money : 2,321,231원
 */
data class LottoNumberInfo(val round : Int, val numbers : String, val money : String, var date : String)