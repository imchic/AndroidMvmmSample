package com.example.imchic.util

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.net.URLEncoder

object APIService {

    // 서버 주소
    const val SERVER_URL = "https://2020capi.census.go.kr/mCAPI/and2020/"

    // 서비스 ID
    const val SURV_ID = "fSurvId"
    const val SURV_ID_CODE = "39"

    val APP_VERSION_CHK = "${SERVER_URL}home/selectAppVersion.do" // 앱 버전 확인

    val LOGIN = "${SERVER_URL}cmm/capiLogin.do?isEncodedYn=Y&hash=sv_${getRandomString(20)}" // 로그인
    val SELECT_PASS_INIT = "${SERVER_URL}cmm/selectPassInit.do" //비밀번호 찾기
    val UPDATE_PASS_INIT_SEND_SMS = "${SERVER_URL}cmm/updatePassInitSendSms.do"
    val SAVE_INIT_PASS = "${SERVER_URL}cmm/saveInitPass.do"

    val SURV_PROC_STATS = "${SERVER_URL}home/selectSurvProcStatsAll.do" // 조사진행률 조회
    val SELECT_SURV_PROC_STATS = "${SERVER_URL}home/selectSurvProcStats.do" // 전체 / 배정 조사구 현황 조회
    val MAIN_HOME_BOARD_LIST = "${SERVER_URL}home/selectMainHomeBoardList.do" // 공지사항(처리지침) 게시판 조회
    val INSERT_SOS_REQUEST = "${SERVER_URL}cmm/insertSOSReqest.do" // 긴급호출 처리
    val INSERT_SOS_REQUEST_HISTORY = "${SERVER_URL}cmm/insertSOSReqestHist.do" // SOS 이력
    val SELECT_CAPI_LTNM_LIST = "${SERVER_URL}hnm/selectfCapiLtnmList.do"

    enum class HEADER(val type: Any) {
        JSON("application/json; charset=utf-8".toMediaTypeOrNull() as MediaType),
        IMAGE("image/jpeg"),
        FILE("multipart/form-data"),
        FORM("application/x-www-form-urlencoded; charset=UTF-8")
    }

    /**
     * API 데이터 가져오기
     * @param headerType String
     * @param url String
     * @param map Map<String, String>
     * @param callback Callback
     * @return String
     */
    fun getAPIResponse(headerType: HEADER, url: String, map: Map<String, String>, callback: Callback) {

        val client = OkHttpClient()
        val request = Request.Builder()

        try {
            val builder = FormBody.Builder()

            if (map.isNotEmpty()) {
                map.forEach { data ->
                    builder.add(data.key, data.value)
                }

                request
                    .url(url)
                    .addHeader("Content-Type", headerType.type.toString())
                    .post(builder.build())
                    .build()
                client.newCall(request.build()).enqueue(callback)
            }

        } catch (e: Exception) {
            throw IllegalAccessError(e.toString())
        }
    }


        /**
         * 랜덤 문자열 생성
         * @param length Int
         * @return String
         */
        private fun getRandomString(length: Int): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

    }

    /**
     * 서비스 API 정리 (필요에 따라 파라미터를 대입하여 사용)
     */
    object CallAPI {

        /**
         * 버전체크 REST API
         */
        fun appVersionCheck() {
            val versionChkMap = mapOf(
                "sysDiv" to APIService.SURV_ID
            )
            APIService.getAPIResponse(APIService.HEADER.FORM, APIService.APP_VERSION_CHK, versionChkMap, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    AppUtil.logE("onFailure $e")
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
                        val result = response.body?.string()
                        AppUtil.logV("onResponse $result")
                    } else {
                        AppUtil.logV("onResponse ${response.code}")
                    }
                }
            })
        }

        /**
         * 로그인 API
         */
        fun login() {
            val loginMap = mapOf(
                "osType" to "LIX",
                "divcType" to "T",
                "divcModel" to "Android",
                "brwsType" to "CH",
                "scrWidth" to "800",
                "scrHeight" to "1208",
                "note" to "",
                "device_type" to "T",
                "sysDiv" to APIService.SURV_ID,
                "usrId" to URLEncoder.encode("um@00013", "UTF-8"),
                "usrPwd" to URLEncoder.encode("2022!!", "UTF-8")
            )
            APIService.getAPIResponse(APIService.HEADER.FORM, APIService.LOGIN, loginMap, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    AppUtil.logE("onFailure $e")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    AppUtil.logV("onResponse $body")
                }
            })
        }

    }