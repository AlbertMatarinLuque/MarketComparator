package cat.copernic.daniel.marketcomparator.ui.products

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.model.ProductsDTO

class ProductsAdapter(val products: List<ProductsDTO>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(){

    init {
        Log.d("Adapter", products.toString())
    }

    inner class ProductsViewHolder: RecyclerView.ViewHolder{
        val txtName : TextView
        constructor(item: View):super(item){
            txtName = item.findViewById(R.id.tvNameProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = products.get(position)
        holder.txtName.setText(item?.nombreProducto)
    }

    override fun getItemCount(): Int {
        return products.size!!
    }
}