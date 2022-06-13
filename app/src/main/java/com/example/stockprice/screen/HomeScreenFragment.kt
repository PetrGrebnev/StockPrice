package com.example.stockprice.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockprice.R
import com.example.stockprice.Repository
import com.example.stockprice.ResultState
import com.example.stockprice.StockApi
import com.example.stockprice.databinding.HomeScreenFragmentBinding
import org.koin.android.ext.android.getKoin

class HomeScreenFragment : Fragment(R.layout.home_screen_fragment) {

    private lateinit var bindingHome: HomeScreenFragmentBinding
    private val binding
        get() = bindingHome

    private lateinit var adapter: ListStocksAdapter
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private fun initHomeScreenViewModel() {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeScreenViewModel(
                    getKoin().get()
                ) as T
            }
        }
        homeScreenViewModel = ViewModelProvider(this, factory).get(HomeScreenViewModel::class.java)
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
        homeScreenViewModel.stocks.observe(viewLifecycleOwner) {
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
                    binding.textError.visibility = View.GONE
                    binding.homeFragmentListStocks.visibility = View.VISIBLE
                    adapter.setListNote(it.data)
                }
            }
        }
    }
}