package cat.copernic.daniel.marketcomparator.ui.configuration.users.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import cat.copernic.daniel.marketcomparator.model.UsuariDTO

class AuthActivityVM: ViewModel() {
    private val repo = Repo()
    fun fetchProductData(): LiveData<UsuariDTO> {
        val mutableData = MutableLiveData<UsuariDTO>()
        repo.getUsername().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}