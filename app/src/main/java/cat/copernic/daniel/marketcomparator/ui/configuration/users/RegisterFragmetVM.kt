package cat.copernic.daniel.marketcomparator.ui.configuration.users

import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragmetVM: ViewModel() {
    var usuari : UsuariDTO = UsuariDTO("", "")
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuaris")

    fun insertDataBBDD(){
        database.child(usuari.nomUsuari).setValue(usuari)
    }

}