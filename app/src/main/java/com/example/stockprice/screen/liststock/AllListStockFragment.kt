package com.example.stockprice.screen.liststock

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockprice.R
import com.example.stockprice.SortOrder
import com.example.stockprice.application.ResultState
import com.example.stockprice.databinding.AllListStockFragmentBinding
import com.example.stockprice.models.database.StockModelDatabase
import com.example.stockprice.screen.SettingsActivity
import org.koin.android.ext.android.getKoin

class AllListStockFragment : Fragment(R.layout.all_list_stock_fragment) {

    private lateinit var bindingAllStock: AllListStockFragmentBinding
    private val binding
        get() = bindingAllStock

    private lateinit var adapter: ListStocksAdapter
    private lateinit var allStockViewModel: AllLIstStockViewModel
    private lateinit var controller: NavController
    private lateinit var sortOrder: SortOrder
    private var liveDataStock: LiveData<ResultState<List<StockModelDatabase>>>? = null
    private val observer: (ResultState<List<StockModelDatabase>>) -> Unit = {
        when (it) {
            is ResultState.Error -> binding.textError.text = "ERROR: " + it.throwable.message
            is ResultState.Loading -> binding.textError.text = "Loading"
            is ResultState.Success -> adapter.setListNote(it.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun initHomeScreenViewModel() {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AllLIstStockViewModel(
                    getKoin().get(),
                    getKoin().get(),
                    getKoin().get()
                ) as T
            }
        }
        allStockViewModel = ViewModelProvider(this, factory).get(AllLIstStockViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHomeScreenViewModel()
        bindingAllStock = AllListStockFragmentBinding.bind(view)
        controller = Navigation.findNavController(view)
        sortOrder = SortOrder.ASC
        adapter = ListStocksAdapter(layoutInflater,
            object : ListStocksAdapter.OnStockClickListener {
                override fun onStockClick(stock: StockModelDatabase) {
                    val bundle = bundleOf(
                        ARGUMENT_SYMBOL_STOCK to stock.symbol,
                        ARGUMENT_NAME_STOCK to stock.nameStock
                    )
                    controller.navigate(
                        R.id.action_allListStockFragment_to_detailsStockFragment,
                        bundle
                    )
                }
            })

        binding.apply {
            allListStocks.adapter = adapter
            allListStocks.layoutManager = LinearLayoutManager(requireContext())
            refresh.setOnRefreshListener {
                allStockViewModel.loadingFromApi()
                refresh.isRefreshing = false
            }
        }

        allStockViewModel.sortOrder.observe(viewLifecycleOwner){
            sortOrder = it
        }

        allStockViewModel.stocks.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Error -> {
                    binding.textError.text = "ERROR: " + it.throwable.message
                    binding.allListStocks.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.textError.text = "Loading"
                    binding.allListStocks.visibility = View.GONE
                }
                is ResultState.Success -> {
                    binding.textError.visibility = View.GONE
                    binding.allListStocks.visibility = View.VISIBLE
                    adapter.setListNote(it.data)
                }
            }
        }
        subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        Toast.makeText(getKoin().get(), "ТУТ МЕНЮ СОЗДАЛОСЬ", Toast.LENGTH_LONG).show()
        val searchViewItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
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
                val intent = Intent ( getKoin().get(), SettingsActivity::class.java)
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
        allStockViewModel.listAllStock(sortOrder)
    }

    companion object {
        private const val ARGUMENT_SYMBOL_STOCK = "ARGUMENT_SYMBOL_STOCK"
        private const val ARGUMENT_NAME_STOCK = "ARGUMENT_NAME_STOCK"
    }
}