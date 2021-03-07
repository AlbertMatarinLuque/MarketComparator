package cat.copernic.daniel.marketcomparator.ui.products.seeProduct

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.model.PreciosSupermercados
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import cat.copernic.daniel.marketcomparator.setViewProduct
import com.bumptech.glide.Glide

class SeeProductAdapter(private val context: Context) :
    RecyclerView.Adapter<SeeProductAdapter.SeeProductsViewHolder>(){

    private var dataList = mutableListOf<PreciosSupermercados>()

    fun setListPricesData(data: MutableList<PreciosSupermercados>) {
        dataList = data
        dataList.sortBy { it.preciosSupermercados }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeProductsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_price_row, parent, false)
        return SeeProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeeProductsViewHolder, position: Int) {
        val prices = dataList[position]
        holder.bindView(prices)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0) {
            return dataList.size
        } else {
            return 0
        }
    }

    inner class SeeProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(prices: PreciosSupermercados) {
            itemView.findViewById<TextView>(R.id.tvNameMarket).setText(prices.mercado.nombreMercado)
            itemView.findViewById<TextView>(R.id.tvPricesMarket).setText(prices.preciosSupermercados.toString() +  "€")
            val media = prices.mercado.imagenSupermercado
            Glide.with(itemView)
                .load(media)
                .into(itemView.findViewById<ImageView>(R.id.imgMarket))
        }
    }

}