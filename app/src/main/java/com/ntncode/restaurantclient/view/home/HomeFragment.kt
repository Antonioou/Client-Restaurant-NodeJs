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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ntncode.restaurantclient.adapter.ItemListOneDesignAdapter
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.Item
import com.ntncode.restaurantclient.databinding.FragmentHomeBinding
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var _rv_itemlist: RecyclerView? = null

    val _listItemResponse = ArrayList<Item>()


    private fun initBinding() {

        _rv_itemlist = _binding?.rvItemslistHome

    }

    private fun initView() {

        RetrofitClient.interceptor.level = HttpLoggingInterceptor.Level.BODY

        _rv_itemlist?.layoutManager = StaggeredGridLayoutManager(2, 1)

    }

    private fun initEvents() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val window: Window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
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

        initBinding()
        initView()
        initEvents()

        getListItems()
    }


    fun getListItems() {

        val call = RetrofitClient.serviceApiUser.ItemList(14)
        call.enqueue(object : retrofit2.Callback<List<Item>> {
            override fun onResponse(
                call: Call<List<Item>>,
                response: Response<List<Item>>
            ) {
                if (response.isSuccessful) {
                    val result: List<Item> = response.body()!!
                    //Log.e("LOG_LISTITEM", "LOG RESULT:\n .${response.body().toString()} \nss")
                    if (response.code() == 200) {
                        val adapter = ItemListOneDesignAdapter(result)
                        _rv_itemlist?.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {

                Log.e("LOG_LISTITEM", "LOG RESULT:\n .${t.message} \nss")
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}