package cat.copernic.daniel.marketcomparator.ui.configuration.addProducts

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class AddProductViewModel : ViewModel(){
    var options : Array<String> = arrayOf("Verd","Blau", "Groc", "Marró", "Gris")
    var numid : Long = 0
    var idProducto : String
    var product : ProductsDTO = ProductsDTO("","",0.0,"")

    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("products")
   // private var lastKey : Query = FirebaseDatabase.getInstance().getReference("products").limitToLast(1)
    private lateinit var context : Context

    // Añadir metodo para saber ultimo producto y aumentar su ID
    init {
        idProducto = "product$numid"

    }
    fun insertarDatosBBDD(){
        database.child(idProducto).setValue(product).addOnSuccessListener {
            showPositiveProductRegisterAlert()
           // Log.i("BBDD", lastKey.)
        }.addOnFailureListener {
            showNegativeProductRegisterAlert()
        }
        incrementarid()
    }

    fun setContext(con: Context){
        context = con
    }


    fun incrementarid(){
        numid++
        idProducto = "product$numid"
    }

    private fun showPositiveProductRegisterAlert(){
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