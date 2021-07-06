package com.example.countries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.CountryAdapter
import com.example.countries.api.ApiHelper
import com.example.countries.api.RetrofitInstance
import com.example.countries.database.CountryDatabase
import com.example.countries.models.CountryItem
import com.example.countries.repository.CountryRepository
import com.example.countries.utils.Status
import com.example.countries.viewmodels.CountryViewModel
import com.example.countries.viewmodels.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryViewModel
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
        btn_retry.setOnClickListener {
            setupObservers()
        }
    }

    private fun setupViewModel() {
        val countryRepository = CountryRepository(ApiHelper(RetrofitInstance.getInstance()), CountryDatabase(this))
        val viewModelProviderFactory = ViewModelFactory(application, countryRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(CountryViewModel::class.java)
    }

    private fun setupUI() {
        rv_countires.layoutManager = LinearLayoutManager(this)
        adapter = CountryAdapter(arrayListOf(), this)
        rv_countires.addItemDecoration(
            DividerItemDecoration(
                rv_countires.context,
                (rv_countires.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_countires.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getAllCountries().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        rv_countires.visibility = View.VISIBLE
                        btn_retry.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        resource.data?.let { countries -> retrieveList(countries) }
                    }
                    Status.ERROR -> {
                        rv_countires.visibility = View.VISIBLE
                        btn_retry.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        btn_retry.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                        rv_countires.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                MaterialAlertDialogBuilder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want do delete from database?")
                        .setPositiveButton("OK"){_,_ ->

                        }
                        .setNegativeButton("CANCEL"){dialog, _ ->
                            dialog.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun retrieveList(users: ArrayList<CountryItem>) {
        adapter.apply {
            addCountries(users)
            notifyDataSetChanged()
        }
    }
}