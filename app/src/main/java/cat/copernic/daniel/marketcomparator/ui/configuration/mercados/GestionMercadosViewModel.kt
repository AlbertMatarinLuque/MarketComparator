package cat.copernic.daniel.marketcomparator.ui.configuration.mercados

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.model.Mercado
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GestionMercadosViewModel: ViewModel() {
    private var database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("markets")

    var market = Mercado("","",0.0,"")

    private lateinit var context: Context

    fun insertarDatosBBDD(nombre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.child(nombre).setValue(market)
                .addOnSuccessListener {
                    showPositiveMarketRegisterAlert()
                }.addOnFailureListener {
                    showNegativeMarketRegisterAlert()
                }
        }
    }

    fun setContext(con: Context) {
        context = con
    }

    private fun showPositiveMarketRegisterAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.rightMessage))
        builder.setMessage("Se ha añadido el mercado correctamente")
        builder.setPositiveButton(context.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNegativeMarketRegisterAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.wrongMessage))
        builder.setMessage("No se ha añadido el mercado correctamente")
        builder.setPositiveButton(context.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}