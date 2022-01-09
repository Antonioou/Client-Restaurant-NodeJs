package com.ntncode.restaurantclient.view.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.`interface`.ItemClickListener
import com.ntncode.restaurantclient.adapter.ItemListOneDesignAdapter
import com.ntncode.restaurantclient.adapter.ItemListTwoDesignAdapter
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.datastore.UserDataStore
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel
import com.ntncode.restaurantclient.data.roomdb.repository.CartRepository
import com.ntncode.restaurantclient.data.sp.SessionSP
import com.ntncode.restaurantclient.databinding.FragmentHomeBinding
import com.ntncode.restaurantclient.model.ItemData
import com.ntncode.restaurantclient.util.dialogs.ConfirmationStyleAlertDialog
import com.ntncode.restaurantclient.view.item.ItemDetailActivity
import com.ntncode.restaurantclient.view.login.OAuthActivity
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    //[MAIN]
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //[DATASTORE]
    private lateinit var dataStoreUserManager: UserDataStore
    private lateinit var sessionSP: SessionSP

    //[VARIABLES]
    lateinit var stateSession: String
    private var nameUser: String? = null

    //[ELEMENTS]
    private var rvItemlist: RecyclerView? = null
    private var rvItemShortlist: RecyclerView? = null

    //[ADAPTER]
    private lateinit var adapterOneD: ItemListOneDesignAdapter
    private lateinit var adapterTwoD: ItemListTwoDesignAdapter

    private val cartRepository: CartRepository by lazy {
        CartRepository(requireContext())
    }


    private fun initBind() {

        rvItemlist = _binding?.rvItemslistHome

        adapterTwoD = ItemListTwoDesignAdapter()


    }

    private fun initRes() {

        RetrofitClient.interceptor.level = HttpLoggingInterceptor.Level.BODY

        dataStoreUserManager = UserDataStore(requireContext())
        sessionSP = SessionSP(requireContext())

        rvItemlist?.layoutManager = StaggeredGridLayoutManager(2, 1)

        rvItemShortlist?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

    }

    private fun initEvents() {

        _binding?.ivProfileHome?.setOnClickListener {

            if (stateSession.equals(getString(R.string.status_denied))) {
                dialogSignSuggestion()
            } else {
                Toast.makeText(requireContext(), "Perfil $stateSession", Toast.LENGTH_SHORT).show()
            }
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

        initBind()
        initRes()

        lifecycleScope.launchWhenCreated {
            asignValueSession(sessionSP.getStateSession())

        }

        dataStoreUserManager.getFirstName.asLiveData().observe(requireActivity(), {
            nameUser = it
            _binding?.tvNameCustomerHome?.text = it
        })


        initEvents()


        //initBottomSheetPreviewCart()

        //getListItems()

        val list: MutableList<ItemData> = mutableListOf()
        //val rndm = (0..444).random()

        val item = ItemData(
            (0..444).random().toString(),
            "coca cola",
            "description coca",
            "https://source.unsplash.com/random",
            1,
            1,
            1,
            "cat",
            1,
            "type name",
            "",
            10.0,
            5
        )
        val item1 = ItemData(
            (0..444).random().toString(),
            "pepsi",
            "description pepsi",
            "https://source.unsplash.com/random",
            1,
            1,
            1,
            "pepe",
            1,
            "type name",
            "",
            34.0,
            6
        )

        list.add(item)
        list.add(item1)

        adapterOneD = ItemListOneDesignAdapter()

        adapterOneD.setItems(list)
        rvItemlist?.adapter = adapterOneD
        adapterOneDClick()

        getListShortCart()
    }


    /*
    *  INIT ITEM CODES
     */

    private fun getListItems() {
        val call = RetrofitClient.serviceApiUser.getItemList(14)
        call.enqueue(object : retrofit2.Callback<List<ItemData>> {
            override fun onResponse(
                call: Call<List<ItemData>>,
                response: Response<List<ItemData>>
            ) {
                if (response.isSuccessful) {
                    val result: List<ItemData> = response.body()!!

                    //Log.e("LOG_LISTITEM", "LOG RESULT: $result")
                    if (response.code() == 200) {

                        /*adapter = ItemListOneDesignAdapter()
                        adapter.setItems(result)
                        rvItemlist?.adapter = adapter*/

                        adapterOneDClick()
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemData>>, t: Throwable) {
                Log.e("LOG_LISTITEM", "LOG RESULT:\n .${t.message} \nss")
            }
        })

    }

    fun adapterOneDClick() {

        adapterOneD.setOnItemClick(object : ItemClickListener.ListClickListener<ItemData> {
            override fun onClick(list: ItemData, position: Int) {
                val i = Intent(requireContext(), ItemDetailActivity::class.java)

                i.apply {
                    putExtra("imageItem", list.image_item)
                    putExtra("idItem", list.id_item)
                    putExtra("nameItem", list.name_item)
                    putExtra("costItem", list.price_detail_item.toString())
                }
                startActivity(i)
            }

            override fun onDelete(list: ItemData) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun getListShortCart() {
        //adapterTwoD = ItemListTwoDesignAdapter()

        //val allCart = cartRepository.getAllCart()
        //Log.e("LOG_HOMEFRAGMENT", "LISTA: \n${allCart.toString()}")
        //val list: MutableList<CartEntityModel> = mutableListOf()


        val list: MutableList<CartEntityModel> = mutableListOf()
        //val rndm = (0..444).random()

        val item = CartEntityModel(
            1, "name", "loc", "https://source.unsplash.com/random"
        )

        list.add(item)

        adapterTwoD.setItems(list)
        //list.add(allCart)
        rvItemShortlist?.adapter = adapterTwoD

        //adapterOneDClick()
        //binding.tvQtyPreviewCart.text = allCart.size.toString()
    }


    /*
    *  CLOSE ITEM CODES
     */


    /*private fun initBottomSheetPreviewCart() {
        val bottomSheetPreviewCart = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(R.layout.bottomsheet_preview_cart, null)


        bottomSheetPreviewCart.setContentView(view)
        bottomSheetPreviewCart.show()
    }*/


    /*
    *  INIT LOGIN CODES
     */
    private fun asignValueSession(state: String?) {
        if (state != null) {
            stateSession = state
        }
    }

    private fun dialogSignSuggestion() {

        val dialog = ConfirmationStyleAlertDialog(requireContext(), true).apply {
            set(
                title = getString(R.string.title_login_dialog),
                message = getString(R.string.message_login_dialog),
                negativeButtonText = getString(R.string.negative_button_login_dialog),
                negativeButtonListener = {
                    dismiss()
                },
                positiveButtonText = getString(R.string.positive_button_login_dialog),
                positiveButtonListener = {
                    startActivity(Intent(requireContext(), OAuthActivity::class.java))
                    dismiss()
                }
            )
        }
        dialog.show()
    }

    /*
    *  CLOSE LOGIN CODES
     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}