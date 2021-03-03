package cat.copernic.daniel.marketcomparator.ui.showUsers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentUsersBinding


class users : Fragment() {
    lateinit var viewModel: UsersVM
    lateinit var binding: FragmentUsersBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentUsersBinding>(
            inflater,
            R.layout.fragment_users,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(UsersVM::class.java)
        recyclerView = binding.recycleView2
        adapter = UsersAdapter(requireContext())
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
        when (id) {
            R.id.trolley -> requireView().findNavController()
                .navigate(R.id.action_productFragment_to_shoppingListFragment)
            R.id.search -> requireView().findNavController()
                .navigate(R.id.action_productFragment_to_searchFragment2)
        }
        return super.onOptionsItemSelected(item)
    }


    fun observeData() {
        binding.shimmerViewContainerUser.startShimmer()
        viewModel.fetchProductData().observe(viewLifecycleOwner, Observer {
            binding.shimmerViewContainerUser.stopShimmer()
            binding.shimmerViewContainerUser.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

}