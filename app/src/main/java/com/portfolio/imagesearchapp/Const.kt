package com.portfolio.imagesearchapp

class Const {

    companion object {
        const val HTTP_TIMEOUT = 30L
        const val MAIN_FRAGMENT = "MainFragment"
        const val PHOTO_FRAGMENT = "PhotoFragment"
    }

    interface KAKAO {
        companion object {
            const val URL = "https://dapi.kakao.com"
            const val SEARCH_IMAGE = "/v2/search/image"
        }

        interface SORT {
            companion object {
                const val ACCURACY = "accuracy"
                const val RECENCY = "recency"
                const val SIZE = 30
            }
        }
    }

    interface HANDLER {
        companion object {
            const val SEARCH_IMAGE = 1;
        }
    }

    interface KEY {
        companion object {
            const val DOCUMENT = "document"
        }
    }
}