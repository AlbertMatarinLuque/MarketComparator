package cat.copernic.daniel.marketcomparator.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cat.copernic.daniel.marketcomparator.model.Mercado
import cat.copernic.daniel.marketcomparator.model.PreciosSupermercados
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import cat.copernic.daniel.marketcomparator.getCurrentUser
import cat.copernic.daniel.marketcomparator.updateNav

class Repo {

    fun getProductsData(): LiveData<MutableList<ProductsDTO>> {
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        FirebaseDatabase.getInstance().reference.child("products").get()
            .addOnSuccessListener { result ->

                val listProducts = mutableListOf<ProductsDTO>()
                var listPrices: MutableList<PreciosSupermercados> =
                    mutableListOf<PreciosSupermercados>()
                for (productsBD in result.children) {

                    for (prices in productsBD.child("listaPrecios").children) {
                        val m: Mercado = Mercado(
                            prices.child("mercado").child("nombreMercado").getValue().toString(),
                            prices.child("mercado").child("descripcionMercado").getValue()
                                .toString(),
                            prices.child("mercado").child("puntuacionMercado").getValue().toString()
                                .toDouble(),
                            prices.child("mercado").child("imagenSupermercado").getValue()
                                .toString()
                        )
                        val l: PreciosSupermercados = PreciosSupermercados(
                            m,
                            prices.child("preciosSupermercados").getValue().toString().toDouble()
                        )
                        listPrices.add(prices.key!!.toInt(), l)
                    }

                    val p: ProductsDTO = ProductsDTO(
                        productsBD.child("nombreProducto").getValue().toString(),
                        productsBD.child("descripcionProducto").getValue().toString(),
                        // productsBD.child("precioProducto").getValue().toString().toDouble(),
                        listPrices,
                        productsBD.child("contenedorProducto").getValue().toString(),
                        productsBD.child("tendenciaProducto").getValue().toString().toInt(),
                        productsBD.child("imagenProducto").getValue().toString()
                    )
                    listProducts.add(p)
                    listPrices = mutableListOf()
                }
                mutableData.value = listProducts
            }
        return mutableData
    }

    fun getProductsTendencia(): LiveData<MutableList<ProductsDTO>> {
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()

        FirebaseDatabase.getInstance().reference.child("products").orderByChild("tendenciaProducto")
            .limitToFirst(8)
            .get().addOnSuccessListener { result ->
                val listProducts = mutableListOf<ProductsDTO>()
                var listPrices: MutableList<PreciosSupermercados> =
                    mutableListOf<PreciosSupermercados>()
                for (productsBD in result.children) {
                    // Recojer Lista de Precios por Supermercado
                    for (prices in productsBD.child("listaPrecios").children) {
                        val m: Mercado = Mercado(
                            prices.child("mercado").child("nombreMercado").getValue().toString(),
                            prices.child("mercado").child("descripcionMercado").getValue()
                                .toString(),
                            prices.child("mercado").child("puntuacionMercado").getValue().toString()
                                .toDouble(),
                            prices.child("mercado").child("imagenSupermercado").getValue()
                                .toString()
                        )
                        val l: PreciosSupermercados = PreciosSupermercados(
                            m,
                            prices.child("preciosSupermercados").getValue().toString().toDouble()
                        )
                        listPrices.add(prices.key!!.toInt(), l)
                    }

                    val p: ProductsDTO = ProductsDTO(
                        productsBD.child("nombreProducto").getValue().toString(),
                        productsBD.child("descripcionProducto").getValue().toString(),
                        listPrices,
                        productsBD.child("contenedorProducto").getValue().toString(),
                        productsBD.child("tendenciaProducto").getValue().toString().toInt(),
                        productsBD.child("imagenProducto").getValue().toString()
                    )
                    listPrices = mutableListOf()
                    listProducts.add(p)
                }
                listProducts.sortByDescending { it.tendenciaProducto }
                mutableData.value = listProducts
            }
        return mutableData
    }

    fun getProductsMasNuevo(): LiveData<MutableList<ProductsDTO>> {
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        FirebaseDatabase.getInstance().reference.child("products").limitToLast(8)
            .get().addOnSuccessListener { result ->
                val listProducts = mutableListOf<ProductsDTO>()
                var listPrices: MutableList<PreciosSupermercados> =
                    mutableListOf<PreciosSupermercados>()
                for (productsBD in result.children) {

                    for (prices in productsBD.child("listaPrecios").children) {
                        val m: Mercado = Mercado(
                            prices.child("mercado").child("nombreMercado").getValue().toString(),
                            prices.child("mercado").child("descripcionMercado").getValue()
                                .toString(),
                            prices.child("mercado").child("puntuacionMercado").getValue().toString()
                                .toDouble(),
                            prices.child("mercado").child("imagenSupermercado").getValue()
                                .toString()
                        )
                        val l: PreciosSupermercados = PreciosSupermercados(
                            m,
                            prices.child("preciosSupermercados").getValue().toString().toDouble()
                        )
                        listPrices.add(prices.key!!.toInt(), l)
                    }

                    val p: ProductsDTO = ProductsDTO(
                        productsBD.child("nombreProducto").getValue().toString(),
                        productsBD.child("descripcionProducto").getValue().toString(),
                        //  productsBD.child("precioProducto").getValue().toString().toDouble(),
                        listPrices,
                        productsBD.child("contenedorProducto").getValue().toString(),
                        productsBD.child("tendenciaProducto").getValue().toString().toInt(),
                        productsBD.child("imagenProducto").getValue().toString()
                    )
                    listProducts.add(p)
                    listPrices = mutableListOf()
                }

                listProducts.sortByDescending { it.tendenciaProducto }
                mutableData.value = listProducts
            }
        return mutableData
    }

    fun getMarketsData(): LiveData<MutableList<Mercado>> {
        val mutableData = MutableLiveData<MutableList<Mercado>>()
        FirebaseDatabase.getInstance().reference.child("markets").get()
            .addOnSuccessListener { result ->

                val listmarkets = mutableListOf<Mercado>()
                for (marketsBD in result.children) {
                    val m: Mercado = Mercado(
                        marketsBD.child("nombreMercado").getValue().toString(),
                        marketsBD.child("descripcionMercado").getValue().toString(),
                        marketsBD.child("puntuacionMercado").getValue().toString().toDouble(),
                        marketsBD.child("imagenSupermercado").getValue().toString()
                    )
                    listmarkets.add(m)

                }
                mutableData.value = listmarkets

            }
        return mutableData
    }


    fun getUsername(): LiveData<UsuariDTO> {
        val mutableData = MutableLiveData<UsuariDTO>()
        val user: FirebaseUser = getCurrentUser()
        FirebaseDatabase.getInstance().reference.child("usuaris/${user.uid}").get()
            .addOnSuccessListener { result ->
                val u: UsuariDTO = UsuariDTO(
                    result.child("nomUsuari").getValue().toString(),
                    result.child("mail").getValue().toString(),
                    result.child("permisos").getValue().toString(),
                    result.child("uid").getValue().toString()
                )

                mutableData.value = u
                updateNav(mutableData)
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
                        usersBD.child("mail").getValue().toString(),
                        usersBD.child("permisos").getValue().toString(),
                        usersBD.child("uid").getValue().toString()
                    )
                    listUsers.add(u)

                }
                mutableData.value = listUsers
            }
        return mutableData
    }

    fun removeUser(uid: String) {
        FirebaseDatabase.getInstance().reference.child("usuaris")
            .child(uid).removeValue()
    }

    fun modifyUser(user: UsuariDTO) {
        FirebaseDatabase.getInstance().reference.child("usuaris")
            .child(user.uid).setValue(user)
    }
}