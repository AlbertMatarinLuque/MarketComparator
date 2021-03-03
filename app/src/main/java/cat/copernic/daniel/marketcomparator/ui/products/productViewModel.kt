package cat.copernic.daniel.marketcomparator.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.ProductsDTO


class productViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchProductData(): LiveData<MutableList<ProductsDTO>> {
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        repo.getProductsData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}