package cat.copernic.daniel.marketcomparator.ui.configuration.users

import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragmetVM: ViewModel() {
    var usuari : UsuariDTO = UsuariDTO("", "")
    var numId: Long = 0
    var idUsuari: String
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuaris")

    init {
        idUsuari = "usuari$numId"
    }

    fun insertDataBBDD(){
        database.child(idUsuari).setValue(usuari)
        incrementarid()
    }

    private fun incrementarid() {
        numId++
        idUsuari = "usuari$numId"
    }

}