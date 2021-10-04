package com.currencyconverter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currencyconverter.data.models.Country
import com.currencyconverter.databinding.ActivityMainBinding
import com.currencyconverter.main.CommonAdapter
import com.currencyconverter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommonAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var fromList : MutableList<Country> = mutableListOf()
    private lateinit var countryListAdapter: CommonAdapter
    private var countryCurrencyName: String? = null
    private var lastPos: Int? = null


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        countryListAdapter = CommonAdapter(binding.root.context, fromList, this)
        binding.fromCurrencyRecyclerView.adapter = countryListAdapter
        binding.fromCurrencyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.fromCurrencyRecyclerView.setHasFixedSize(true)
        bindCountriesData()
        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                countryCurrencyName?.let {countryCurrencyName}
                    ?:{countryCurrencyName = "EUR"} as String,
                binding.spToCurrency.selectedItem.toString(),
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when(event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        val clickedItem = fromList[position]
        countryCurrencyName = clickedItem.getCountryCurrency()
        countryListAdapter.notifyItemChanged(position)
    }

    private fun bindCountriesData() {
        fromList.add(Country("USD","USA"))
        fromList.add(Country("EUR","Europe"))
    }
}