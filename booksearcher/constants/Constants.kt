package com.hsh.booksearcher.constants
class Constants {
    companion object{
        const val BASE_URL = "https://dapi.kakao.com"

        private const val REST_API_KEY = "c6470432f356f656acdfaee02ff1af7e"

        // 개인 API 사용
        const val AUTH_HEADER = "KakaoAK $REST_API_KEY"

        const val FRAGMENT_STATE_SEARCH = "SEARCH"
        const val FRAGMENT_STATE_DETAIL_RESULT = "DETAIL_RESULT"
    }
}