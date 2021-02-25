package cat.copernic.daniel.marketcomparator.ui.home

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.ui.products.productViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.title)
        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.trolley ->   requireView().findNavController().navigate(R.id.action_nav_home_to_shoppingListFragment)
            R.id.search ->   requireView().findNavController().navigate(R.id.action_nav_home_to_searchFragment2)
        }
        return super.onOptionsItemSelected(item)
    }
}