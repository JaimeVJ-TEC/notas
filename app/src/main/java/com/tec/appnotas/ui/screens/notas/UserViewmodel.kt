package com.tec.appnotas.ui.screens.notas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.NotaRepository
import com.tec.appnotas.domain.repository.NotaRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(
    private val notaRepositoryImp: NotaRepository
) : ViewModel(){
    private val _listaNotas = MutableStateFlow<List<Nota>>(listOf())
    val listaNotas: StateFlow<List<Nota>> = _listaNotas

    init {
        viewModelScope.launch(Dispatchers.IO) {
            notaRepositoryImp.getLocalNotas(false)
                .collect { notas ->
                    _listaNotas.value = notas
                }
        }
    }

    suspend fun insertNota(): Nota{
        var nota = Nota()
        nota.notaId = notaRepositoryImp.insertLocalNota(nota)
        Log.d("NOTA",nota.notaId.toString())
        return nota
    }

    fun getNotaFromCode(id: String) {
        viewModelScope.launch {
            try {
                val nota = notaRepositoryImp.getNota(id)
                notaRepositoryImp.insertLocalNota(nota)
            }
            catch (e: Exception){
                Log.d("LOL","LMAO, EVEN")
            }
        }

    }
}