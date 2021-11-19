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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.adapter.ItemListOneDesignAdapter
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.datastore.UserDataStore
import com.ntncode.restaurantclient.data.sp.SessionSP
import com.ntncode.restaurantclient.databinding.FragmentHomeBinding
import com.ntncode.restaurantclient.model.ItemData
import com.ntncode.restaurantclient.util.dialogs.ConfirmationStyleAlertDialog
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
    lateinit var state_session: String

    //[ELEMENTS]
    private var rvItemlist: RecyclerView? = null

    val listItemResponse = ArrayList<ItemData>()


    private fun initBind() {

        rvItemlist = _binding?.rvItemslistHome

    }

    private fun initRes() {

        RetrofitClient.interceptor.level = HttpLoggingInterceptor.Level.BODY

        dataStoreUserManager = UserDataStore(requireContext())
        sessionSP = SessionSP(requireContext())

        rvItemlist?.layoutManager = StaggeredGridLayoutManager(2, 1)

    }

    private fun initEvents() {

        _binding?.ivProfileHome?.setOnClickListener {

            if (state_session.equals(getString(R.string.status_denied))){
                dialogSignSuggestion()
            }else{
                Toast.makeText(requireContext(), "Perfil $state_session", Toast.LENGTH_SHORT).show()
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

        initEvents()


        getListItems()
    }

    private fun asignValueSession(state: String?) {
        state_session = state ?: getString(R.string.status_denied)
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

    private fun getListItems() {
        //Log.e("LOG_LISTITEM", "INIT LIST")
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