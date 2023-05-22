import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.NotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class NotasViewModel @Inject constructor(
//    private val userRepositoryImp: NotaRepository
//): ViewModel() {
//
//    private val _listaNotas = MutableStateFlow<List<Nota>>(listOf())
//    val listaNotas: StateFlow<List<Nota>> = _listaNotas
//
////    init {
////        viewModelScope.launch {
////            userRepositoryImp.getLocalNotas(false)
////                .collect { notas ->
////                    _listaNotas.value = notas
////                }
////        }
////    }
//
//
//    fun getNota(id: String){
//        viewModelScope.launch(Dispatchers.IO) {
//            val nota = userRepositoryImp.getNota("eq.$id")
//            Log.d("NOTA",nota.title)
//        }
//    }
//}