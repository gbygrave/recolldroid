package org.grating.recolldroid.data

import retrofit2.http.GET

interface RecollApiService {
    @GET("recoll")
    suspend fun queryRecoll(): List<RecollResult>
}