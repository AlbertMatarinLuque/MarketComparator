package cat.copernic.daniel.marketcomparator.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PreciosSupermercados(
    var mercado: Mercado,
    var preciosSupermercados: Double,

)
