package cat.copernic.daniel.marketcomparator.ui.configuration.addProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentAddProductsBinding
import com.google.android.material.snackbar.Snackbar


class AddProductsFragment : Fragment() {

    private lateinit var binding: FragmentAddProductsBinding
    private lateinit var opciones: Spinner
    private lateinit var viewModel : AddProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
        viewModel.setContext(requireContext())
        binding=  DataBindingUtil.inflate<FragmentAddProductsBinding>(
            inflater,
            R.layout.fragment_add_products, container, false
        )
            opciones = binding.spContenedor

        // Esteblecer valores del Spinner
        opciones.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.options
        )


        binding.btnInsert.setOnClickListener {
            if(binding.edProductName.text.isEmpty() || binding.edDescription.text.isEmpty() ||
                    binding.edPrice.text.isEmpty()) {
                Snackbar.make(requireView(),getString(R.string.emptyFields),Snackbar.LENGTH_SHORT).show()

            }else {
                viewModel.product.nombreProducto = binding.edProductName.text.toString()
                viewModel.product.descripcionProducto = binding.edDescription.text.toString()
                viewModel.product.precioProducto = binding.edPrice.text.toString().toDouble()
                viewModel.product.contenedorProducto = binding.spContenedor.selectedItem.toString()
                viewModel.insertarDatosBBDD()
            }
        }

        return binding.root
    }


    override fun onStop() {
        viewModel.product.nombreProducto = binding.edProductName.text.toString()
        viewModel.product.descripcionProducto = binding.edDescription.text.toString()
        viewModel.product.precioProducto = binding.edPrice.text.toString().toDouble()
        viewModel.product.contenedorProducto = binding.spContenedor.toString()
        super.onStop()
    }

    override fun onResume() {
        binding.edProductName.setText(viewModel.product.nombreProducto)
        binding.edDescription.setText(viewModel.product.descripcionProducto)
        binding.edPrice.setText(viewModel.product.precioProducto.toString())
        //binding.spContenedor.set
        super.onResume()
    }
}