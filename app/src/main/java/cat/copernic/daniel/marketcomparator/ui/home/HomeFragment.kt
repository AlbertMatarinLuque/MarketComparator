package cat.copernic.daniel.marketcomparator.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TendenciasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        recyclerView = binding.recycleView
        adapter = TendenciasAdapter(requireContext())
        recyclerView.adapter = adapter
        observeData()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.title)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.trolley -> requireView().findNavController()
                .navigate(R.id.action_nav_home_to_shoppingListFragment)
            R.id.search -> requireView().findNavController()
                .navigate(R.id.action_nav_home_to_searchFragment2)
        }
        return super.onOptionsItemSelected(item)
    }

    fun observeData() {
        binding.shimmerViewContainer.startShimmer()
        viewModel.fetchProductData().observe(viewLifecycleOwner, Observer {
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            adapter.setListTendencias(it)
            adapter.notifyDataSetChanged()
        })
    }
}