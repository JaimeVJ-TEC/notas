package com.tec.appnotas

import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.datasource.RestDataSource
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.NotaRepositoryImp
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.nio.charset.StandardCharsets

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NotasRepositoryUnitTest {

    private val mockWebServer = MockWebServer().apply(){
        url("/")
        dispatcher = myDispatcher
    }

    private val restDataSource = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestDataSource::class.java)

    private val notasRespository = NotaRepositoryImp(restDataSource, MockNotaDao())

}

val myDispatcher: Dispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "rest/v1/notas" -> MockResponse().apply { addResponse("api_nota_response.json") }
            else -> MockResponse().setResponseCode(404)
        }
    }
}

fun MockResponse.addResponse(filePath: String): MockResponse {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        setResponseCode(200)
        setBody(it.readString(StandardCharsets.UTF_8))
    }
    return this
}