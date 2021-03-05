package cat.copernic.daniel.marketcomparator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.Mercado
import cat.copernic.daniel.marketcomparator.model.ProductsDTO

class MainViewModel: ViewModel() {




    private val repo = Repo()
    fun fetchMarketData(): LiveData<MutableList<Mercado>> {
        val mutableData = MutableLiveData<MutableList<Mercado>>()
        repo.getMarketsData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}