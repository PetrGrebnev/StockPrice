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

class DetailsStockFragment : Fragment(R.layout.details_stokc_fragment) {

    private lateinit var detailsStockFragmentBinding: DetailsStokcFragmentBinding
    private val binding
        get() = detailsStockFragmentBinding

    private lateinit var detailsViewModel: DetailsStockViewModel
    private lateinit var controller: NavController

    private val args: DetailsStockFragmentArgs by navArgs()

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
        val stockSymbol = args.symbol
        val nameStock = args.nameStock
        initViewModel(stockSymbol)

        detailsViewModel.stock.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Error -> {
                    binding.apply {
                        progressBarDetailsStockFragment.visibility = View.GONE
                        symbolStockDetailsFragment.text = getString(R.string.error_details)
                        nameCurrencyDetailsFragment.visibility = View.GONE
                        datetimeDetailsFragment.visibility = View.GONE
                        nameExchangeDetailsFragment.visibility = View.GONE
                        priceCloseDetailsFragment.visibility = View.GONE
                        nameDetailsFragment.text = nameStock
                    }
                }
                is ResultState.Loading -> binding.progressBarDetailsStockFragment.visibility = View.VISIBLE
                is ResultState.Success -> {
                    binding.apply {
                        progressBarDetailsStockFragment.visibility = View.GONE
                        val result = it.data
                        symbolStockDetailsFragment.text = stockSymbol
                        nameCurrencyDetailsFragment.text = result.currency
                        datetimeDetailsFragment.text = result.datetime
                        nameExchangeDetailsFragment.text = result.exchange
                        priceCloseDetailsFragment.text = result.closePrice.toString()
                        nameDetailsFragment.text = nameStock
                        if (result.avatar == null) {
                            avatar.visibility = View.GONE
                        } else {
                            Glide
                                .with(view)
                                .load(result.avatar)
                                .into(avatar)
                        }
                    }
                }
            }
        }
    }
}