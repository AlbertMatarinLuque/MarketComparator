package cat.copernic.daniel.marketcomparator.ui.products

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.daniel.marketcomparator.R


class productFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.trolley ->   requireView().findNavController().navigate(R.id.action_productFragment_to_shoppingListFragment)
            R.id.search ->   requireView().findNavController().navigate(R.id.action_productFragment_to_searchFragment2)
        }
        return super.onOptionsItemSelected(item)
    }


}