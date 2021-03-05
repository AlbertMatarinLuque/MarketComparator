package cat.copernic.daniel.marketcomparator.ui.products.seeProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TabHost
import androidx.databinding.DataBindingUtil
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentSeeProductBinding
import cat.copernic.daniel.marketcomparator.model.ProductsDTO
import cat.copernic.daniel.marketcomparator.getViewProduct
import com.bumptech.glide.Glide


class SeeProductFragment : Fragment() {
    lateinit var binding: FragmentSeeProductBinding
    var product: ProductsDTO = ProductsDTO("","",mutableListOf(),"",0,"")
    private lateinit var tabHost: TabHost

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSeeProductBinding>(
            inflater,
            R.layout.fragment_see_product,
            container,
            false)

        inicializarHostTab()


        product = getViewProduct()
        setData()
        return binding.root
    }




    fun setData(){

       Glide.with(requireContext())
            .load(product.imagenProducto)
            .into(binding.imageViewProduct)

        binding.tvName.text = product.nombreProducto
        binding.tvDescription.text = product.descripcionProducto
        binding.tvContenedor.text = product.contenedorProducto
    }

    fun inicializarHostTab(){
        tabHost = binding.tabHost
        tabHost.setup()

        // Tab 1
        var tabSpec : TabHost.TabSpec
        tabSpec = tabHost.newTabSpec("INFO")
        tabSpec.setContent(R.id.tab1)
        tabSpec.setIndicator("Informació")
        tabHost.addTab(tabSpec)

        //Tab 2
        tabSpec = tabHost.newTabSpec("PRECIOS")
        tabSpec.setContent(R.id.tab2)
        tabSpec.setIndicator("Preus")
        tabHost.addTab(tabSpec)

        // Tab 3
        tabSpec = tabHost.newTabSpec("CONTENEDOR")
        tabSpec.setContent(R.id.tab3)
        tabSpec.setIndicator("Reciclatge")
        tabHost.addTab(tabSpec)
    }

}