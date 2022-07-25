package com.example.stockprice.screen.liststock

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockprice.R
import com.example.stockprice.utils.SortOrder
import com.example.stockprice.utils.ResultState
import com.example.stockprice.databinding.AllListStockFragmentBinding
import com.example.stockprice.datamodels.database.StockModelDatabase
import com.example.stockprice.screen.activity.SettingsActivity
import org.koin.android.ext.android.getKoin

class AllListStockFragment : Fragment(R.layout.all_list_stock_fragment) {

    private lateinit var bindingAllStock: AllListStockFragmentBinding
    private val binding
        get() = bindingAllStock

    private lateinit var adapter: ListStocksAdapter
    private lateinit var allStockViewModel: AllLIstStockViewModel
    private var liveDataStock: LiveData<ResultState<List<StockModelDatabase>>>? = null
    private val observer: (ResultState<List<StockModelDatabase>>) -> Unit = {
        when (it) {
            is ResultState.Error -> binding.textError.text = "ERROR: " + it.throwable.message
            is ResultState.Loading -> binding.progressBarListStockFragment.visibility =
                View.VISIBLE
            is ResultState.Success -> adapter.setListNote(it.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initHomeScreenViewModel()
    }

    private fun initHomeScreenViewModel() {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AllLIstStockViewModel(
                    getKoin().get(),
                ) as T
            }
        }
        allStockViewModel = ViewModelProvider(this, factory).get(AllLIstStockViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingAllStock = AllListStockFragmentBinding.bind(view)
        adapterCreate()

        binding.apply {
            binding.allListStocks.layoutManager = LinearLayoutManager(getKoin().get())
            binding.allListStocks.adapter = adapter
            refresh.setOnRefreshListener {
                allStockViewModel.setSortOrder(SortOrder.ASC)
                refresh.isRefreshing = false
            }
        }
    }

    private fun adapterCreate() {
        adapter = ListStocksAdapter(layoutInflater) { symbol, nameStock ->
            findNavController().navigate(
                AllListStockFragmentDirections.actionAllListStockFragmentToDetailsStockFragment(
                    symbol, nameStock
                )
            )
        }
        binding.allListStocks.layoutManager = LinearLayoutManager(getKoin().get())
        binding.allListStocks.adapter = adapter
        observeStocks(adapter)
    }

    private fun observeStocks(adapter: ListStocksAdapter) {
        allStockViewModel.stocks.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Error -> {
                    binding.textError.text = "ERROR: " + it.throwable.message
                    binding.allListStocks.visibility = View.GONE
                    binding.progressBarListStockFragment.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.textError.visibility = View.GONE
                    binding.progressBarListStockFragment.visibility = View.VISIBLE
                    binding.allListStocks.visibility = View.GONE
                }
                is ResultState.Success -> {
                    binding.textError.visibility = View.GONE
                    binding.progressBarListStockFragment.visibility = View.GONE
                    binding.allListStocks.visibility = View.VISIBLE
                    adapter.setListNote(it.data)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchViewItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query.also { allStockViewModel.query = it }
                allStockViewModel.setSortOrder(SortOrder.SEARCH)
                subscribe()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    allStockViewModel.setSortOrder(allStockViewModel.oldSortOrder)
                    subscribe()
                } else {
                    newText.toString().also { allStockViewModel.query = it }
                    allStockViewModel.setSortOrder(SortOrder.SEARCH)
                    subscribe()
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_asc -> {
                allStockViewModel.setSortOrder(SortOrder.ASC)
                subscribe()
            }
            R.id.sort_desc -> {
                allStockViewModel.setSortOrder(SortOrder.DESC)
                subscribe()
            }
            R.id.settings -> {
                val intent = Intent(getKoin().get(), SettingsActivity::class.java)
                activity?.finish()
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        liveDataStock?.removeObserver(observer)
        liveDataStock = allStockViewModel.stocks.also {
            it.observe(viewLifecycleOwner, observer)
        }
        allStockViewModel.sortOrder.observe(viewLifecycleOwner){ sortOrder ->
            allStockViewModel.listAllStock(sortOrder)
        }
    }
}