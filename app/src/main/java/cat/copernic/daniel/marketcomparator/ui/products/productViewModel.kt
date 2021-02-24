package cat.copernic.daniel.marketcomparator.ui.products

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class productViewModel: ViewModel() {
    init {
    //getAllProducts()

    }





    fun getAllProducts(): MutableList<ProductsDTO>{
        var products : MutableList<ProductsDTO> =  mutableListOf()
            val querry = FirebaseDatabase.getInstance().reference.child("products")

           viewModelScope.launch(Dispatchers.IO) {
               querry.addValueEventListener(object : ValueEventListener {
                   override fun onDataChange(snapshot: DataSnapshot) {
                       for (productsBD in snapshot.children) {
                           val p: ProductsDTO = ProductsDTO(productsBD.child("nombreProducto").getValue().toString(),
                                   productsBD.child("descripcionProducto").getValue().toString(),
                                   productsBD.child("precioProducto").getValue().toString().toDouble(),
                                   productsBD.child("contenedorProducto").getValue().toString())
                           products.add(p)


                       }

                       Log.d("Product", products.toString())
                   }

                   override fun onCancelled(error: DatabaseError) {

                   }
               })
           }
            return products
    }



}