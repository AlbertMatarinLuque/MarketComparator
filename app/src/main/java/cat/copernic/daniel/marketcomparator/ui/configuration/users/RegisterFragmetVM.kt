package cat.copernic.daniel.marketcomparator.ui.configuration.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragmetVM: ViewModel() {
    var usuari : UsuariDTO = UsuariDTO("", "")
    private lateinit var mAuth: FirebaseAuth
    lateinit var currentUser: FirebaseUser
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuaris")


    fun insertDataBBDD(){
        database.child(currentUser.uid).setValue(usuari)
    }

    fun getUsername(): String {
        var username = ""
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser!!
        viewModelScope.launch(Dispatchers.IO) {
            val query = FirebaseDatabase.getInstance().reference.child("usuaris")
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (usuarisBD in snapshot.children) {
                        if (usuarisBD.key.toString() == currentUser.uid) {
                                username = usuarisBD.child("nomUsuari").toString()
                                Log.i("Usuario",username)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
        return username
    }




}