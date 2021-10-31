package com.ntncode.restaurantclient.view.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ntncode.restaurantclient.adapter.ItemListOneDesignAdapter
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.ItemData
import com.ntncode.restaurantclient.databinding.FragmentHomeBinding
import com.ntncode.restaurantclient.datastore.UserDataStore
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    //[MAIN]
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //[DATASTORE]
    private lateinit var dataStoreUserManager: UserDataStore


    //[ELEMENTS]
    private var rvItemlist: RecyclerView? = null

    val listItemResponse = ArrayList<ItemData>()


    private fun initBind() {

        rvItemlist = _binding?.rvItemslistHome

    }

    private fun initRes() {

        RetrofitClient.interceptor.level = HttpLoggingInterceptor.Level.BODY

        dataStoreUserManager = UserDataStore(requireContext())

        rvItemlist?.layoutManager = StaggeredGridLayoutManager(2, 1)

    }

    private fun initEvents() {

        _binding?.ivProfileHome?.setOnClickListener {

            var name: String? = null

            dataStoreUserManager.getPhoneNumber.asLiveData().observe(requireActivity(), {
                if (it != null) {
                    Log.e("LOG_LISTITEM", "LOG RESULT: $it")
                }
            })

            /*
            lifecycleScope.launch {
                name = dataStoreUserManager.getPhoneNumber()
                Log.e("LOG_LISTITEM", "LOG RESULT: $name")
            }*/

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val window: Window = requireActivity().window
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        val wic = WindowCompat.getInsetsController(window, window.decorView)
        wic?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        wic?.isAppearanceLightStatusBars = true
        window.statusBarColor = Color.TRANSPARENT

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*binding.buttonFirst.setOnClickListener {
findNavController().navigate(R.id.action_HomeFragment_to_ProfileCustomerFragment2)
}*/

        initBind()
        initRes()
        initEvents()

        getListItems()
    }


    private fun getListItems() {

        val call = RetrofitClient.serviceApiUser.ItemList(14)
        call.enqueue(object : retrofit2.Callback<List<ItemData>> {
            override fun onResponse(
                call: Call<List<ItemData>>,
                response: Response<List<ItemData>>
            ) {
                if (response.isSuccessful) {
                    val result: List<ItemData> = response.body()!!

                    if (response.code() == 200) {
                        val adapter = ItemListOneDesignAdapter(result)
                        rvItemlist?.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemData>>, t: Throwable) {

                Log.e("LOG_LISTITEM", "LOG RESULT:\n .${t.message} \nss")
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}