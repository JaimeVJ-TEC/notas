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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.glxn.qrgen.android.QRCode
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(
    private val notaRepositoryImp: NotaRepository
) : ViewModel(){
    val listaNotas: Flow<List<Nota>> = notaRepositoryImp.getLocalNotas(false)
    val listaNotasArchived: Flow<List<Nota>> = notaRepositoryImp.getLocalNotas(true)

    private val _userState = MutableStateFlow(UserVMState.NORMAL)
    val userState: StateFlow<UserVMState> = _userState

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

    fun notifiedError(){
        _userState.value = UserVMState.NORMAL
    }

    suspend fun insertNota(nota: Nota): Nota{
        _userState.value = UserVMState.LOADING
        nota.notaId = notaRepositoryImp.insertLocalNota(nota)
        _userState.value = UserVMState.NORMAL
        return nota
    }

    fun getNotaFromCode(id: String) {
        if(!inserting && _userState.value != UserVMState.LOADING) {
            _userState.value = UserVMState.LOADING
            inserting = true
            viewModelScope.launch {
                try {
                    val nota = notaRepositoryImp.getNota(id)
                    insertNota(nota)
                    _userState.value = UserVMState.NORMAL
                }
                catch(e: Exception){
                    _userState.value = UserVMState.CONNECTION_ERROR
                }
                finally {
                    inserting = false
                }
            }
        }
    }

    fun archiveNota(id: Int, archive: Boolean){
        _userState.value = UserVMState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            var nota = notaRepositoryImp.getNotaById(id)
            nota.archived = archive
            notaRepositoryImp.updateLocalNota(nota)
            _userState.value = UserVMState.NORMAL
        }
    }

    fun deleteNota(id: Int){
        _userState.value = UserVMState.LOADING
        viewModelScope.launch(Dispatchers.IO){
            var nota = notaRepositoryImp.getNotaById(id)
            notaRepositoryImp.deleteLocalNota(nota)
            _userState.value = UserVMState.NORMAL
        }
    }

    suspend fun shareNota(id: Int): Bitmap?{
        _userState.value = UserVMState.LOADING
        return try {
            var nota = notaRepositoryImp.getNotaById(id)
            var response = notaRepositoryImp.postNota(nota)
            _userState.value = UserVMState.NORMAL
            QRCode.from(response.id).withSize(500, 500).bitmap()
        } catch(e: Exception){
            _userState.value = UserVMState.CONNECTION_ERROR
            null
        }
    }
}

enum class UserVMState{
    LOCAL_STORAGE_ERROR,
    CONNECTION_ERROR,
    LOADING,
    NORMAL
}