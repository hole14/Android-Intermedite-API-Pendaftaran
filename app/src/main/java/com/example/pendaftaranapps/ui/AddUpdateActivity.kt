package com.example.pendaftaranapps.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pendaftaranapps.R
import com.example.pendaftaranapps.data.respone.AddUpdateResponse
import com.example.pendaftaranapps.data.respone.DataItem
import com.example.pendaftaranapps.data.retrofit.APIConfig
import com.example.pendaftaranapps.databinding.ActivityAddUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUpdateBinding
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val siswa = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_DATA, DataItem::class.java)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        if (siswa != null){
            id = siswa.id!!.toInt()

            binding.btnDelete.visibility = View.VISIBLE
            binding.elId.visibility = View.VISIBLE
            binding.etId.setText(siswa.id)
            binding.btnSaveUpdate.text = getString(R.string.update)
            binding.etNama.setText(siswa.nama)
            binding.etAlamat.setText(siswa.alamat)
            binding.etJk.setText(siswa.jenisKelamin)
            binding.etAgama.setText(siswa.agama)
            binding.etSekolahAsal.setText(siswa.sekolahAsal)
        }

        binding.btnSaveUpdate.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val jenisKelamin = binding.etJk.text.toString()
            val agama = binding.etAgama.text.toString()
            val sekolahAsal = binding.etSekolahAsal.text.toString()

            if(nama.isEmpty()){
                binding.etNama.error = getString(R.string.error)
            }else if(alamat.isEmpty()){
                binding.etAlamat.error = getString(R.string.error)
            }else if(jenisKelamin.isEmpty()){
                binding.etJk.error = getString(R.string.error)
            }else if(agama.isEmpty()){
                binding.etAgama.error = getString(R.string.error)
            }else if(sekolahAsal.isEmpty()){
                binding.etSekolahAsal.error = getString(R.string.error)
            }else{
                if (binding.btnSaveUpdate.text == getString(R.string.save)) {
                    insertSiswa(nama, alamat, jenisKelamin, agama, sekolahAsal)
                }else if (binding.btnSaveUpdate.text == getString(R.string.update)){
                    updateSiswa(id, nama, alamat, jenisKelamin, agama, sekolahAsal)
                }
            }
        }
        binding.btnDelete.setOnClickListener {
            deleteSiswa(id)
        }
    }

    private fun deleteSiswa(id: Int) {
        showLoading(true)
        val client = APIConfig.getApiService().deleteSiswa(id)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "onFailure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<AddUpdateResponse>,
                response: Response<AddUpdateResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        Toast.makeText(this@AddUpdateActivity, "succes", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUpdateActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@AddUpdateActivity, "null", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@AddUpdateActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun updateSiswa(
        id: Int,
        nama: String,
        alamat: String,
        jenisKelamin: String,
        agama: String,
        sekolahAsal: String
    ) {
        showLoading(true)
        val client = APIConfig.getApiService().updateSiswa(id, nama, alamat, jenisKelamin, agama, sekolahAsal)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "onFailure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<AddUpdateResponse>,
                response: Response<AddUpdateResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        Toast.makeText(this@AddUpdateActivity, "succes", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUpdateActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@AddUpdateActivity, "null", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@AddUpdateActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun insertSiswa(
        nama: String,
        alamat: String,
        jenisKelamin: String,
        agama: String,
        sekolahAsal: String
    ) {
        showLoading(true)
        val client = APIConfig.getApiService().addSiswa(nama, alamat, jenisKelamin, agama, sekolahAsal)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AddUpdateResponse>, response: Response<AddUpdateResponse>) {
                showLoading(false)
                if (response.isSuccessful){

                    val responseBody = response.body()

                    if (responseBody != null){
                        Toast.makeText(this@AddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUpdateActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@AddUpdateActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@AddUpdateActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}