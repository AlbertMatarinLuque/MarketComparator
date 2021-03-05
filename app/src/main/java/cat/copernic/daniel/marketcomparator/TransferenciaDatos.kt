package cat.copernic.daniel.marketcomparator

import cat.copernic.daniel.marketcomparator.model.Mercado
import cat.copernic.daniel.marketcomparator.model.ProductsDTO

private var product: ProductsDTO = ProductsDTO("","", mutableListOf(),"",0,"")
private var mercados: MutableList<Mercado> = mutableListOf()

fun setViewProduct(p:ProductsDTO){
    product = p
}

fun getViewProduct(): ProductsDTO{
    return product
}

fun addMercado(){

}

fun getMercados(): List<Mercado>{
    return mercados
}