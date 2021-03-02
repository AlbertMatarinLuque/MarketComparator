package cat.copernic.daniel.marketcomparator.ui.configuration.users.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragmetVM: ViewModel() {
    var usuari : UsuariDTO = UsuariDTO("", "")
    lateinit var currentUser: FirebaseUser
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuaris")
    private val repo = Repo()

    fun insertDataBBDD(){
        database.child(currentUser.uid).setValue(usuari)
    }


    fun fetchProductData(): LiveData<UsuariDTO> {
        val mutableData = MutableLiveData<UsuariDTO>()
        repo.getUsername().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}