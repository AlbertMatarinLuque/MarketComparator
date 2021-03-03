package cat.copernic.daniel.marketcomparator.ui.configuration.users.register

import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class RegisterFragmetVM : ViewModel() {
    var usuari: UsuariDTO = UsuariDTO("", "")
    lateinit var currentUser: FirebaseUser
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuaris")

    fun insertDataBBDD() {
        database.child(currentUser.uid).setValue(usuari)
    }
}