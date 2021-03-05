package cat.copernic.daniel.marketcomparator.ui.configuration.addProducts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentAddProductsBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class AddProductsFragment : Fragment() {

    private lateinit var binding: FragmentAddProductsBinding
    private lateinit var opciones: Spinner
    private lateinit var viewModel: AddProductViewModel
    private lateinit var mStorage: StorageReference
    private val GALLERY_INTENT: Int = 1
    private var uri: Uri? = null
    private var image = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Afegir producte"
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
        viewModel.setContext(requireContext())
        binding = DataBindingUtil.inflate<FragmentAddProductsBinding>(
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

        //Storage
        mStorage = FirebaseStorage.getInstance().getReference()


        binding.btnInsert.setOnClickListener {
            if (binding.edProductName.text.isEmpty() || binding.edDescription.text.isEmpty() ||
                binding.edPrice.text.isEmpty()
            ) {
                Snackbar.make(requireView(), getString(R.string.emptyFields), Snackbar.LENGTH_SHORT)
                    .show()

            } else {

                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent, GALLERY_INTENT)


            }
        }

        return binding.root
    }


    override fun onStop() {
        viewModel.product.nombreProducto = binding.edProductName.text.toString()
        viewModel.product.descripcionProducto = binding.edDescription.text.toString()
       // viewModel.product.precioProducto = binding.edPrice.text.toString().toDouble()
        viewModel.product.contenedorProducto = binding.spContenedor.toString()
        super.onStop()
    }

    override fun onResume() {
        binding.edProductName.setText(viewModel.product.nombreProducto)
        binding.edDescription.setText(viewModel.product.descripcionProducto)
      //  binding.edPrice.setText(viewModel.product.precioProducto.toString())
        //binding.spContenedor.set
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            uri = data?.data

            var filePath: StorageReference =
                mStorage.child("FotosProductos").child(uri!!.lastPathSegment!! + ".png")

            var uploadTask: StorageTask<*>
            uploadTask = filePath.putFile(uri!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task?.exception?.let {
                        throw it
                    }
                }
                return@Continuation filePath.downloadUrl
            }).addOnCompleteListener {
                if (it.isSuccessful) {
                    viewModel.product.nombreProducto = binding.edProductName.text.toString()
                    viewModel.product.descripcionProducto = binding.edDescription.text.toString()
                   // viewModel.product.precioProducto = binding.edPrice.text.toString().toDouble()
                    viewModel.product.contenedorProducto =
                        binding.spContenedor.selectedItem.toString()
                    viewModel.product.tendenciaProducto = 0
                    viewModel.product.imagenProducto = it.result.toString()
                    viewModel.insertarDatosBBDD()
                }
            }
        }
    }
}



