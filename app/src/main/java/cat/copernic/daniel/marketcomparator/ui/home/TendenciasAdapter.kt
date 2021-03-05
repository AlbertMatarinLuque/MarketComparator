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
import cat.copernic.daniel.marketcomparator.setViewProduct

class TendenciasAdapter(private val context: Context) :
    RecyclerView.Adapter<TendenciasAdapter.TendenciaViewHolder>() {

    private var dataList = mutableListOf<ProductsDTO>()

    fun setListTendencias(data: MutableList<ProductsDTO>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TendenciaViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_tendenciamasnuevo, parent, false)
        return TendenciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TendenciaViewHolder, position: Int) {
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

    inner class TendenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(product: ProductsDTO) {
            itemView.findViewById<TextView>(R.id.tvNameProduct).setText(product.nombreProducto)
            //itemView.findViewById<TextView>(R.id.tvPriceProduct).setText(product.precioProducto.toString() + "€")
            itemView.findViewById<TextView>(R.id.tvContainerProduct)
                .setText(product.contenedorProducto)
            val media = product.imagenProducto
            Glide.with(itemView)
                .load(media)
                .into(itemView.findViewById<ImageView>(R.id.imageView))
            itemView.setOnClickListener {
                setViewProduct(product)
                itemView.findNavController().navigate(R.id.action_nav_home_to_seeProductFragment)
            }
        }
    }
}