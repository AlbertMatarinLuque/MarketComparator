package cat.copernic.daniel.marketcomparator.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.ProductsDTO

class HomeViewModel : ViewModel() {

    private val repo = Repo()

    fun fetchProductData(): LiveData<MutableList<ProductsDTO>> {
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        repo.getProductsTendencia().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}