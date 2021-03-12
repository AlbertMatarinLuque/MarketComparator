package cat.copernic.daniel.marketcomparator.ui.configuration.configuration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentMasInfoUbicacionBinding
import com.bumptech.glide.Glide


class MasInfoUbicacionFragment : Fragment() {
    lateinit var binding: FragmentMasInfoUbicacionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.Location)
        binding = DataBindingUtil.inflate<FragmentMasInfoUbicacionBinding>(
            inflater,
            R.layout.fragment_mas_info_ubicacion,
            container,
            false
        )
        val media =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/IESNicolauCopernic.jpg/520px-IESNicolauCopernic.jpg"
        Glide.with(requireActivity())
            .load(media)
            .into(binding.ivPictureINS)
        return binding.root
    }
}