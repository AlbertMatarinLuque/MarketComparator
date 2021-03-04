package cat.copernic.daniel.marketcomparator.ui.configuration.users.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class RegisterFragmetVM : ViewModel() {
    var usuari: UsuariDTO = UsuariDTO("", "", "default")
    lateinit var currentUser: FirebaseUser
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuaris")

    fun insertDataBBDD() {
        Log.e("user",currentUser.uid.toString())
        database.child(currentUser.uid).setValue(usuari)
    }

    private val repo = Repo()
    fun fetchProductData(): LiveData<UsuariDTO> {

        val mutableData = MutableLiveData<UsuariDTO>()
        repo.getUsername().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}