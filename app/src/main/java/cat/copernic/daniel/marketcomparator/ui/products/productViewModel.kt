package cat.copernic.daniel.marketcomparator.ui.products

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class productViewModel: ViewModel() {
    private val repo = Repo()
    fun fetchProductData(): LiveData<MutableList<ProductsDTO>>{
        val mutableData = MutableLiveData<MutableList<ProductsDTO>>()
        repo.getProductsData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}