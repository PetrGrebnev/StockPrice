package com.example.stockprice.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockprice.R
import com.example.stockprice.databinding.HomeSearchFragmentBinding
import org.koin.android.ext.android.getKoin

class HomeSearchFragment: Fragment(R.layout.home_search_fragment) {

    private lateinit var bindingHomeSearchFragment: HomeSearchFragmentBinding
    private val binding
        get() = bindingHomeSearchFragment

    private lateinit var homeSearchViewModel: HomeSearchViewModel

    private lateinit var controller: NavController


    private fun initViewModel(){
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeSearchViewModel(
                    getKoin().get()
                ) as T
            }
        }
        homeSearchViewModel = ViewModelProvider(this, factory).get(HomeSearchViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingHomeSearchFragment = HomeSearchFragmentBinding.bind(view)
        controller = Navigation.findNavController(view)
        initViewModel()

        binding.allStockButton.setOnClickListener{
            controller.navigate(R.id.action_homeSearchFragment_to_allListStockFragment)
        }
    }
}