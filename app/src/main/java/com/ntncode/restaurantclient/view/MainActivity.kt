package com.ntncode.restaurantclient.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.databinding.ActivityMainBinding
import com.ntncode.restaurantclient.data.datastore.LoginDataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //[MAIN]
    private lateinit var binding: ActivityMainBinding

    //[DATA STORE]


    //[VARIABLES]



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }


}