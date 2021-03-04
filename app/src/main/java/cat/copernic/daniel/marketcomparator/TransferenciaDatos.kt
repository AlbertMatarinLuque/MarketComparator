package cat.copernic.daniel.marketcomparator

import cat.copernic.daniel.marketcomparator.model.Mercado
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser

private var product: ProductsDTO = ProductsDTO("","", mutableListOf(),"",0,"")
private var mercados: MutableList<Mercado> = mutableListOf()
private var currentUser: FirebaseUser? = null
private var navView: NavigationView? = null

fun setViewProduct(p:ProductsDTO){
    product = p
}

fun getViewProduct(): ProductsDTO{
    return product
}

fun addMercado(m: MutableList<Mercado>){
    mercados.addAll(m)
}

fun getMercados(): List<Mercado>{
    return mercados
}

fun setcurrentUser(current: FirebaseUser){
    currentUser = current
}

fun getCurrentUser(): FirebaseUser{
    return currentUser!!
}

fun setnav(nav: NavigationView?){
    navView = nav
}

fun getnav(): NavigationView{
    return navView!!
}