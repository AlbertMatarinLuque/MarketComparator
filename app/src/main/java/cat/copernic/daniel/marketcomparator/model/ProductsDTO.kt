package cat.copernic.daniel.marketcomparator.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductsDTO(
    var nombreProducto : String ,
    var descripcionProducto : String ,
    var precioProducto : Double,
    var contenedorProducto : String
)
