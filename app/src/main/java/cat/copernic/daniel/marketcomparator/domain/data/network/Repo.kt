package cat.copernic.daniel.marketcomparator.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import com.google.firebase.database.FirebaseDatabase

class Repo {

    fun getProductsData():LiveData<MutableList<ProductsDTO>>{
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        FirebaseDatabase.getInstance().reference.child("products").get().addOnSuccessListener {result ->
            val listProducts = mutableListOf<ProductsDTO>()
            for (productsBD in result.children){
                val p: ProductsDTO = ProductsDTO(productsBD.child("nombreProducto").getValue().toString(),
                        productsBD.child("descripcionProducto").getValue().toString(),
                        productsBD.child("precioProducto").getValue().toString().toDouble(),
                        productsBD.child("contenedorProducto").getValue().toString())
                listProducts.add(p)

            }
            mutableData.value = listProducts
        }
        return mutableData
    }
}