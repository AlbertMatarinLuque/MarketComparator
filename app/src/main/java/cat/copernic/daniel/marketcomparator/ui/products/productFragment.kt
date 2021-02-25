package cat.copernic.daniel.marketcomparator.ui.products

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentCategoryBinding
import cat.copernic.daniel.marketcomparator.model.ProductsDTO


class productFragment : Fragment() {
    lateinit var viewModel: productViewModel
    lateinit var binding: FragmentCategoryBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProductsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentCategoryBinding>(inflater,R.layout.fragment_category,container,false)
        viewModel = ViewModelProvider(this).get(productViewModel::class.java)
        recyclerView = binding.recycleView
        // Inflate the layout for this fragment
        adapter = ProductsAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        observeData()
        setHasOptionsMenu(true)
        return binding.root
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


    fun observeData(){
        binding.shimmerViewContainer.startShimmer()
        viewModel.fetchProductData().observe(viewLifecycleOwner, Observer {
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }


}