package cat.copernic.daniel.marketcomparator.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import cat.copernic.daniel.marketcomparator.setViewProduct
import com.bumptech.glide.Glide

class ProductsAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var dataList = mutableListOf<ProductsDTO>()

    fun setListData(data: MutableList<ProductsDTO>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = dataList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0) {
            return dataList.size
        } else {
            return 0
        }
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(product: ProductsDTO) {
            itemView.findViewById<TextView>(R.id.tvNameProduct).setText(product.nombreProducto)
            itemView.findViewById<TextView>(R.id.tvPriceProduct)
                .setText(product.precioProducto.toString() + "€")
            itemView.findViewById<TextView>(R.id.tvContainerProduct)
                .setText(product.contenedorProducto)
            val media = product.imagenProducto
            Glide.with(itemView)
                .load(media)
                .into(itemView.findViewById<ImageView>(R.id.imageView))
            itemView.setOnClickListener {
                setViewProduct(product)
                itemView.findNavController()
                    .navigate(R.id.action_productFragment_to_seeProductFragment)
            }
        }
    }

}