package cat.copernic.daniel.marketcomparator.ui.configuration.addProducts

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Double.parseDouble

class AddProductViewModel : ViewModel(){
    var options : Array<String> = arrayOf("Azul","Verde","Amarillo","Marron","Gris")
    var numid : Long = 0
    var idProducto : String
    var product : ProductsDTO = ProductsDTO("","",0.0,"","")

    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("products")

    private lateinit var context : Context

    init {
        idProducto = "product$numid"
        getLastIDFirebase()
    }

    fun insertarDatosBBDD(){
        viewModelScope.launch(Dispatchers.IO) {
            database.child(idProducto).setValue(product)
                    .addOnSuccessListener {
                        showPositiveProductRegisterAlert()
                    }.addOnFailureListener {
                        showNegativeProductRegisterAlert()
                    }
        }


    }

    fun setContext(con: Context){
        context = con
    }


    fun incrementarid(){
        numid++
        idProducto = "product$numid"
    }

    fun getLastIDFirebase(){
        viewModelScope.launch(Dispatchers.Main) {
            val querry = FirebaseDatabase.getInstance().reference.child("products").limitToLast(1)

            withContext(Dispatchers.IO) {
            querry.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (products in snapshot.children) {
                        numid = getNumericValues(products.key.toString()).toLong()
                        incrementarid()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
        }
    }

    fun getNumericValues(cadena: String): String {

        val sb = StringBuilder()

        for (i in cadena.indices) {
            var numeric = true
            try {
                val num = parseDouble(cadena[i].toString())
            } catch (e: NumberFormatException) {
                numeric = false
            }

            if (numeric) {
                //es un valor numerico.
                sb.append(cadena[i].toString())
            } else {
                //no es valor numerico.
            }

        }

        return sb.toString();
    }

    private fun showPositiveProductRegisterAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.rightMessage))
        builder.setMessage(context.getString(R.string.verifyC))
        builder.setPositiveButton(context.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNegativeProductRegisterAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.wrongMessage))
        builder.setMessage(context.getString(R.string.verifyF))
        builder.setPositiveButton(context.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}