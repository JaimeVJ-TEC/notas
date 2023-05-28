package com.tec.appnotas.ui.screens.notas

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tec.appnotas.domain.models.Event
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.EventRepository
import com.tec.appnotas.domain.repository.NotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.glxn.qrgen.android.QRCode
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(
    private val notaRepositoryImp: NotaRepository
) : ViewModel(){
    val listaNotas: Flow<List<Nota>> = notaRepositoryImp.getLocalNotas(false)
    val listaNotasArchived: Flow<List<Nota>> = notaRepositoryImp.getLocalNotas(true)
    //val listaEventos: Flow<List<Event>> = EventRepository.getEventos()
    private var inserting = false

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            notaRepositoryImp.getLocalNotas(false)
//                .collect { notas ->
//                    _listaNotas.value = notas
//                }
//        }
    }

    suspend fun insertNota(nota: Nota): Nota{
        nota.notaId = notaRepositoryImp.insertLocalNota(nota)
        Log.d("NOTA",nota.notaId.toString())
        return nota
    }

    fun getNotaFromCode(id: String) {
        if(!inserting) {
            inserting = true
            viewModelScope.launch {
                Log.d("TEST", "TEST1")
                val nota = notaRepositoryImp.getNota(id)
                insertNota(nota)
                inserting = false
            }
        }
    }

    fun archiveNota(id: Int, archive: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            var nota = notaRepositoryImp.getNotaById(id)
            nota.archived = archive
            notaRepositoryImp.updateLocalNota(nota)
        }
    }

    fun deleteNota(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            var nota = notaRepositoryImp.getNotaById(id)
            notaRepositoryImp.deleteLocalNota(nota)
        }
    }

    suspend fun shareNota(id: Int): Bitmap{
        var nota = notaRepositoryImp.getNotaById(id)
        var response = notaRepositoryImp.postNota(nota)
        return QRCode.from(response.id).withSize(500,500).bitmap()
    }
}

enum class UserVMState{
    CONNECTION_ERROR,
    LOADING
}