package cat.copernic.daniel.marketcomparator.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UsuariDTO(
    var nomUsuari: String,
    var mail: String,
    var permisos: String
)
