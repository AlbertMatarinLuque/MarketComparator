package cat.copernic.daniel.marketcomparator.ui.configuration.mercados

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentGestionMercadosBinding
import cat.copernic.daniel.marketcomparator.hideKeyBoard
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class GestionMercados : Fragment() {

    private lateinit var binding: FragmentGestionMercadosBinding
    private lateinit var viewModel: GestionMercadosViewModel
    private lateinit var mStorage: StorageReference
    private val GALLERY_INTENT: Int = 1
    private var uri: Uri? = null
    private var image = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentGestionMercadosBinding>(
            inflater,
            R.layout.fragment_gestion_mercados, container, false
        )

        viewModel = ViewModelProvider(this).get(GestionMercadosViewModel::class.java)
        viewModel.setContext(requireContext())
        //Storage
        mStorage = FirebaseStorage.getInstance().getReference()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Afegir mercat"

        binding.btnAddMarket.setOnClickListener {
            if (binding.tvNombreMercado.text.isEmpty()) {
                Snackbar.make(requireView(), getString(R.string.emptyFields), Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent, GALLERY_INTENT)
            }
            hideKeyBoard(requireActivity())
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            uri = data?.data

            var filePath: StorageReference =
                mStorage.child("FotosMercados").child(uri!!.lastPathSegment!! + ".png")

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
                    viewModel.market.nombreMercado = binding.tvNombreMercado.text.toString()
                    viewModel.market.descripcionMercado =
                        binding.tvDescripcionMercado.text.toString()
                    viewModel.market.puntuacionMercado =
                        binding.tvPuntuaconMercado.text.toString().toDouble()
                    viewModel.market.imagenSupermercado = it.result.toString()
                    viewModel.insertarDatosBBDD(binding.tvNombreMercado.text.toString())
                }
            }
        }
    }
}