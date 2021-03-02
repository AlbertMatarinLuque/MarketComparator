package cat.copernic.daniel.marketcomparator.ui.showUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.UsuariDTO

class UsersVM: ViewModel() {
    private val repo = Repo()
    fun fetchProductData(): LiveData<MutableList<UsuariDTO>> {
        val mutableData = MutableLiveData<MutableList<UsuariDTO>>()
        repo.getUsers().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}