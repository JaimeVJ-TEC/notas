package com.tec.appnotas.domain.datasource

import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.models.post.PostItem
import com.tec.appnotas.domain.models.response.NotasResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface RestDataSource {
    companion object {
        const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZ4dm9za3hyaGVhamR3aml1d29qIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODQyODcwMTUsImV4cCI6MTk5OTg2MzAxNX0.F0sb8UDG4ztLvbE_IWEUY3iZyFYf7TMjHLwL9iW-hVE"
    }

    @Headers("apikey: $ACCESS_TOKEN")
    @GET("rest/v1/notas")
    suspend fun getNotaById(@Query("id") id: String): NotasResponse

    @Headers("apikey: $ACCESS_TOKEN","Prefer: return=representation")
    @POST("rest/v1/notas")
    suspend fun postNota(nota: PostItem): NotasResponse

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("/storage/v1/object/{Key}")
    @Streaming
    suspend fun getImagenByURL(@Path("Key") Key: String): ResponseBody

//    TODO
//    suspend fun postImagenDB()
}