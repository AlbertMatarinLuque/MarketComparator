package cat.copernic.daniel.marketcomparator.domain.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repo {
    private lateinit var mAuth: FirebaseAuth
    lateinit var currentUser: FirebaseUser

    fun getProductsData(): LiveData<MutableList<ProductsDTO>> {
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        FirebaseDatabase.getInstance().reference.child("products").get()
            .addOnSuccessListener { result ->
                val listProducts = mutableListOf<ProductsDTO>()
                for (productsBD in result.children) {
                    val p: ProductsDTO = ProductsDTO(
                        productsBD.child("nombreProducto").getValue().toString(),
                        productsBD.child("descripcionProducto").getValue().toString(),
                        productsBD.child("precioProducto").getValue().toString().toDouble(),
                        productsBD.child("contenedorProducto").getValue().toString(),
                        productsBD.child("tendenciaProducto").getValue().toString().toInt(),
                        productsBD.child("imagenProducto").getValue().toString()
                    )
                    listProducts.add(p)

                }
                mutableData.value = listProducts
            }
        return mutableData
    }

    fun getProductsTendencia(): LiveData<MutableList<ProductsDTO>> {

        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        FirebaseDatabase.getInstance().reference.child("products").orderByChild("tendenciaProducto")
            .get().addOnSuccessListener { result ->
            val listProducts = mutableListOf<ProductsDTO>()
            for (productsBD in result.children) {

                val p: ProductsDTO = ProductsDTO(
                    productsBD.child("nombreProducto").getValue().toString(),
                    productsBD.child("descripcionProducto").getValue().toString(),
                    productsBD.child("precioProducto").getValue().toString().toDouble(),
                    productsBD.child("contenedorProducto").getValue().toString(),
                    productsBD.child("tendenciaProducto").getValue().toString().toInt(),
                    productsBD.child("imagenProducto").getValue().toString()
                )
                listProducts.add(p)



            }
            listProducts.sortByDescending { it.tendenciaProducto }
            mutableData.value = listProducts
        }
        return mutableData
    }

    fun getUsername(): LiveData<UsuariDTO> {
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser!!
        val mutableData = MutableLiveData<UsuariDTO>()
        FirebaseDatabase.getInstance().reference.child("usuaris").get()
            .addOnSuccessListener { result ->
                var user: UsuariDTO? = null
                for (usuarisBD in result.children) {
                    if (usuarisBD.key.toString() == currentUser.uid) {
                        val u: UsuariDTO = UsuariDTO(
                            usuarisBD.child("nomUsuari").getValue().toString(),
                            usuarisBD.child("mail").getValue().toString()
                        )
                        user = u
                    }
                }
                mutableData.value = user
            }
        return mutableData
    }

    fun getUsers(): LiveData<MutableList<UsuariDTO>> {
        val mutableData = MutableLiveData<MutableList<UsuariDTO>>()
        FirebaseDatabase.getInstance().reference.child("usuaris").get()
            .addOnSuccessListener { result ->
                val listUsers = mutableListOf<UsuariDTO>()
                for (usersBD in result.children) {
                    val u: UsuariDTO = UsuariDTO(
                        usersBD.child("nomUsuari").getValue().toString(),
                        usersBD.child("mail").getValue().toString()
                    )
                    listUsers.add(u)

                }
                mutableData.value = listUsers
            }
        return mutableData
    }
}