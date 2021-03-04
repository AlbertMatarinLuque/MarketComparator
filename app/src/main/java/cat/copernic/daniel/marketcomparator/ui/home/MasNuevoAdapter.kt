package cat.copernic.daniel.marketcomparator.ui.home

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
import com.bumptech.glide.Glide

class MasNuevoAdapter(private val context: Context) :
    RecyclerView.Adapter<MasNuevoAdapter.LoMasNuevoViewHolder>() {

    private var dataList = mutableListOf<ProductsDTO>()

    fun setListMasNuevos(data: MutableList<ProductsDTO>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoMasNuevoViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_tendenciamasnuevo, parent, false)
        return LoMasNuevoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoMasNuevoViewHolder, position: Int) {
        val tend = dataList[position]
        holder.bindView(tend)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0) {
            return dataList.size
        } else {
            return 0
        }
    }

    inner class LoMasNuevoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                itemView.findNavController().navigate(R.id.action_nav_home_to_seeProductFragment)
            }
        }
    }
}