package cat.copernic.daniel.marketcomparator.ui.products.seeProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentCategoryBinding
import cat.copernic.daniel.marketcomparator.databinding.FragmentSeeProductBinding


class SeeProductFragment : Fragment() {
    lateinit var binding: FragmentSeeProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentSeeProductBinding>(inflater,R.layout.fragment_see_product,container,false)
        return binding.root
    }


}