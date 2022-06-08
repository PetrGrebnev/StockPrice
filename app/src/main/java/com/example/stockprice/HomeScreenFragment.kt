package com.example.stockprice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockprice.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : Fragment(R.layout.home_screen_fragment) {

    private lateinit var bindingHome: HomeScreenFragmentBinding
    private val binding
        get() = bindingHome

    private lateinit var adapter: ListStocksAdapter
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private fun initHomeScreenViewModel(){
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeScreenViewModel(
                    Repository(StockApi.create())
                ) as T
            }
        }
        homeScreenViewModel = ViewModelProvider(this, factory)[HomeScreenViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingHome = HomeScreenFragmentBinding.bind(view)
        adapter = ListStocksAdapter(layoutInflater)
        initHomeScreenViewModel()
        binding.apply {
                homeFragmentListStocks.adapter = adapter
            homeFragmentListStocks.layoutManager = LinearLayoutManager(requireContext())
        }
        homeScreenViewModel.stocks.observe(viewLifecycleOwner){
            when (it) {
                is ResultState.Error -> {
                    binding.textError.text = "ERROR: " + it.throwable.message
                    binding.homeFragmentListStocks.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.textError.text = "Loanding"
                    binding.homeFragmentListStocks.visibility = View.GONE
                }
                is ResultState.Success -> {
                    binding.homeFragmentListStocks.visibility = View.VISIBLE
                    adapter.setListNote(it.data)
                }
            }
        }
    }
}