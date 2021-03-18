package cat.copernic.daniel.marketcomparator.ui.configuration.addProducts

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.addMercado
import cat.copernic.daniel.marketcomparator.databinding.FragmentAddProductsBinding
import cat.copernic.daniel.marketcomparator.getMercados
import cat.copernic.daniel.marketcomparator.model.PreciosSupermercados
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import cat.copernic.daniel.marketcomparator.hideKeyBoard


class AddProductsFragment : Fragment() {

    private lateinit var binding: FragmentAddProductsBinding
    private lateinit var opciones: Spinner
    private lateinit var opcionesMarket: Spinner
    private lateinit var viewModel: AddProductViewModel
    private lateinit var mStorage: StorageReference
    private val GALLERY_INTENT: Int = 1
    private var uri: Uri? = null
    private var image = ""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        createChannel(
            getString(R.string.channel_id),
            getString(R.string.channel_name)
        )
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Afegir producte"
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
        viewModel.setContext(requireContext())
        binding = DataBindingUtil.inflate<FragmentAddProductsBinding>(
            inflater,
            R.layout.fragment_add_products, container, false
        )
        opciones = binding.spContenedor
        opcionesMarket = binding.spMercados


        // Establecer valores del Spinner
        opciones.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.options
        )

        viewModel.fetchMarketData().observe(viewLifecycleOwner, Observer {
            for(market in it) {
                viewModel.optionsMarket.add(market.nombreMercado)
            }
            opcionesMarket.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                viewModel.optionsMarket
            )
        })

        //Storage
        mStorage = FirebaseStorage.getInstance().getReference()


        binding.btnInsert.setOnClickListener {
            if (binding.edProductName.text.isEmpty() || binding.edDescription.text.isEmpty()
            ) {
                Snackbar.make(requireView(), getString(R.string.emptyFields), Snackbar.LENGTH_SHORT)
                    .show()
            } else {

                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent, GALLERY_INTENT)
            }
            hideKeyBoard(requireActivity())
        }

        binding.btnAddPrice.setOnClickListener {
            addPrice()
            hideKeyBoard(requireActivity())
            Snackbar.make(
                requireView(),
                "Se ha añadido correctamente el precio en el mercado : ${binding.spMercados.selectedItem.toString()} con un valor de " +
                        "${binding.edPrice.text}€",
                Snackbar.LENGTH_LONG
            ).show()
            hideKeyBoard(requireActivity())
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
                    viewModel.product.listaPrecios = viewModel.listPrices
                    viewModel.product.contenedorProducto =
                        binding.spContenedor.selectedItem.toString()
                    viewModel.product.tendenciaProducto = 0
                    viewModel.product.imagenProducto = it.result.toString()
                    viewModel.insertarDatosBBDD()

                }
            }
        }
    }


    fun addPrice() {
        for (m in getMercados()) {
            if (m.nombreMercado == binding.spMercados.selectedItem.toString() && binding.edPrice.text.isNotEmpty()) {
                var item: PreciosSupermercados = PreciosSupermercados(
                    m, binding.edPrice.text.toString().toDouble()
                )
                viewModel.listPrices.add(item)
            }
        }


    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}



