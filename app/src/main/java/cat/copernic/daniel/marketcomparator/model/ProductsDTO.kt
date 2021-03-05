package cat.copernic.daniel.marketcomparator.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductsDTO(
    var nombreProducto: String,
    var descripcionProducto: String,
    var listaPrecios: MutableList<PreciosSupermercados>,
    var contenedorProducto: String,
    var tendenciaProducto: Int,
    var imagenProducto: String
)
