package com.example.stockprice.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockprice.R
import com.example.stockprice.ResultState
import com.example.stockprice.databinding.AllListStockFragmentBinding
import org.koin.android.ext.android.getKoin

class AllListStockFragment : Fragment(R.layout.all_list_stock_fragment) {

    private lateinit var bindingAllStock: AllListStockFragmentBinding
    private val binding
        get() = bindingAllStock

    private lateinit var adapter: ListStocksAdapter
    private lateinit var allStockViewModel: AllLIstStockViewModel

    private fun initHomeScreenViewModel() {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AllLIstStockViewModel(
                    getKoin().get(),
                    getKoin().get()
                ) as T
            }
        }
        allStockViewModel = ViewModelProvider(this, factory).get(AllLIstStockViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingAllStock = AllListStockFragmentBinding.bind(view)
        adapter = ListStocksAdapter(layoutInflater)
        initHomeScreenViewModel()
        binding.apply {
            allListStocks.adapter = adapter
            allListStocks.layoutManager = LinearLayoutManager(requireContext())
        }
        allStockViewModel.stocks.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Error -> {
                    binding.textError.text = "ERROR: " + it.throwable.message
                    binding.allListStocks.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.textError.text = "Loanding"
                    binding.allListStocks.visibility = View.GONE
                }
                is ResultState.Success -> {
                    binding.textError.visibility = View.GONE
                    binding.allListStocks.visibility = View.VISIBLE
                    adapter.setListNote(it.data)
                }
            }
        }
    }
}