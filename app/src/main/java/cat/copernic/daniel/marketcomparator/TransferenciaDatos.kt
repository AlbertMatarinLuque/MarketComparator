package cat.copernic.daniel.marketcomparator

import cat.copernic.daniel.marketcomparator.model.ProductsDTO

var product: ProductsDTO = ProductsDTO("","",0.0,"",0,"")

fun setViewProduct(p:ProductsDTO){
    product = p
}

fun getViewProduct(): ProductsDTO{
    return product
}