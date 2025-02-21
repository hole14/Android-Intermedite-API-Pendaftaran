package com.example.pendaftaranapps.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pendaftaranapps.data.respone.DataItem
import com.example.pendaftaranapps.data.respone.ListSiswaResponse
import com.example.pendaftaranapps.data.retrofit.APIConfig
import com.example.pendaftaranapps.databinding.ActivityMainBinding
import com.example.pendaftaranapps.ui.adapter.ListSiswaAdapter
import com.example.pendaftaranapps.ui.addupdatedelete.AddUpdateActivity
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rcListSiswa.layoutManager = layoutManager

        val itemDecoration = androidx.recyclerview.widget.DividerItemDecoration(this, layoutManager.orientation)
        binding.rcListSiswa.addItemDecoration(itemDecoration)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.listSiswa.observe(this) { listSiswa ->
            setSiswaData(listSiswa)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

//        findAllSiswa()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddUpdateActivity::class.java))
        }

        binding.fabDelete.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddUpdateActivity::class.java))

        }
    }

//    private fun findAllSiswa() {
//        showLoading(true)
//        val client = APIConfig.getApiService().getAllSiswa()
//        client.enqueue(object : retrofit2.Callback<ListSiswaResponse> {
//            override fun onFailure(call: Call<ListSiswaResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//                Toast.makeText(this@MainActivity, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<ListSiswaResponse>, response: Response<ListSiswaResponse>) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setSiswaData(responseBody.data)
//                    }
//                }else{
//                    Log.d(TAG, "onFailure: ${response.message()}")
//                    Toast.makeText(this@MainActivity, "onFailure: ${response.message()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        })
//    }

    private fun setSiswaData(dataSiswa: List<DataItem>) {
        val adapter = ListSiswaAdapter()
        adapter.submitList(dataSiswa)
        binding.rcListSiswa.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}