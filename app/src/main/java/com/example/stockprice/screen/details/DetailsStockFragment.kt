package com.example.stockprice.screen.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.stockprice.R
import com.example.stockprice.utils.ResultState
import com.example.stockprice.databinding.DetailsStokcFragmentBinding
import org.koin.android.ext.android.getKoin
import kotlin.properties.Delegates

class DetailsStockFragment : Fragment(R.layout.details_stokc_fragment) {

    private lateinit var detailsStockFragmentBinding: DetailsStokcFragmentBinding
    private val binding
        get() = detailsStockFragmentBinding

    private lateinit var detailsViewModel: DetailsStockViewModel
    private lateinit var controller: NavController

    private val argSymbol: DetailsStockFragmentArgs by navArgs()
    private val argName: DetailsStockFragmentArgs by navArgs()

    private var stockSymbol by Delegates.notNull<String>()
    private var nameStock by Delegates.notNull<String>()

    private fun initViewModel(stockSymbol: String) {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return DetailsStockViewModel(
                    stockSymbol,
                    getKoin().get(),
                ) as T
            }
        }
        detailsViewModel = ViewModelProvider(this, factory).get(DetailsStockViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsStockFragmentBinding = DetailsStokcFragmentBinding.bind(view)
        controller = Navigation.findNavController(view)
        stockSymbol = argSymbol.symbol
        nameStock = argName.nameStock
        initViewModel(stockSymbol)

        detailsViewModel.stock.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Error -> {
                    binding.progressBarDetailsStockFragment.visibility = View.GONE
                    binding.symbolStockDetailsFragment.text =
                        "ERROR " + it.throwable.message
                    binding.nameCurrencyDetailsFragment.visibility = View.GONE
                    binding.datetimeDetailsFragment.visibility = View.GONE
                    binding.nameExchangeDetailsFragment.visibility = View.GONE
                    binding.priceCloseDetailsFragment.visibility = View.GONE
                    binding.nameDetailsFragment.text = nameStock
                }
                is ResultState.Loading -> binding.progressBarDetailsStockFragment.visibility = View.VISIBLE
                is ResultState.Success -> {
                    binding.progressBarDetailsStockFragment.visibility = View.GONE
                    val result = it.data
                    binding.symbolStockDetailsFragment.text = stockSymbol
                    binding.nameCurrencyDetailsFragment.text = result.currency
                    binding.datetimeDetailsFragment.text = result.datetime
                    binding.nameExchangeDetailsFragment.text = result.exchange
                    binding.priceCloseDetailsFragment.text = result.closePrice.toString()
                    binding.nameDetailsFragment.text = nameStock
                    if (result.avatar == null){
                        binding.avatar.visibility = View.GONE
                    } else {
                        Glide
                            .with(view)
                            .load(result.avatar)
                            .into(binding.avatar)
                    }
                }
            }
        }
    }
}