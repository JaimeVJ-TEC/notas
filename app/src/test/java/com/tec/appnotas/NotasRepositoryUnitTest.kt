package com.tec.appnotas

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.datasource.RestDataSource
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.NotaRepositoryImp
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
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

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `Non Archived Notes on the DB are retrieved correctly`(){
        val notas = notasRespository.getLocalNotas(false)
        runBlocking {
            assertEquals(1,notas.first().size)
        }
    }

    @Test
    fun `Archived Notes on the DB are retrieved correctly`(){
        val notas = notasRespository.getLocalNotas(true)
        runBlocking {
            assertEquals(1,notas.first().size)
        }
    }

    @Test
    fun `Note is retrieved correctly by its ID`(){
        runBlocking {
            val nota = notasRespository.getNotaById(2)
            assertEquals("MockNota2", nota.title)
        }
    }

    @Test
    fun `Note is deleted correctly`(){
        runBlocking {
            notasRespository.deleteLocalNota(nota1)
            val notas = notasRespository.getLocalNotas(false)
            assertEquals(0,notas.first().size)
        }
    }

    @Test
    fun `Note is updated correctly`(){
        runBlocking {
            var nota = nota1
            nota.content = "changed content"
            notasRespository.updateLocalNota(nota)
            val updatedNota = notasRespository.getNotaById(nota.notaId)
            assertEquals("changed content",updatedNota.content)
        }
    }

    @Test
    fun `Non existing Note is not updated`(){
        runBlocking {
            var nota = Nota(4,"TITLE","CONTENT",false)
            nota.content = "changed content"
            notasRespository.updateLocalNota(nota)
            val updatedNota = notasRespository.getNotaById(nota.notaId)
            assertEquals(-1,updatedNota.notaId)
        }
    }

    @Test
    fun `Note is inserted correctly into DB`(){
        runBlocking {
            val nota = Nota(title = "Nota 3", content = "Contenido", archived = false)
            notasRespository.insertLocalNota(nota)
            val insertedNota = notasRespository.getNotaById(3)
            assertEquals("Nota 3", insertedNota.title)
        }
    }

    @Test
    fun `Note is fetched correctly`(){
        runBlocking {
            val nota = notasRespository.getNota("9de78c0c-78cb-4d0f-b3ef-7e5d23ee0c5c")
            assertEquals("API RESPONSE",nota.title)
        }
    }

    @Test
    fun `Note is POSTED correctly`(){
        runBlocking {
            var nota = Nota(title = "Test",content = "Content",archived = false)
            val response = notasRespository.postNota(nota)
            nota.title = response.title
            assertEquals("9de78c0c-78cb-4d0f-b3ef-7e5d23ee0c5c",response.id)
            assertEquals("API RESPONSE",nota.title)
        }
    }

}

val myDispatcher: Dispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when {
            request.method == "GET" && request.requestUrl?.queryParameter("id") != null -> MockResponse().apply { addResponse("api_nota_response.json") }
            request.method == "POST" -> MockResponse().apply { addResponse("api_nota_response.json") }
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