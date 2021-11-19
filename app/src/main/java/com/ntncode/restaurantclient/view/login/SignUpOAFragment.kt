package com.ntncode.restaurantclient.view.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.datastore.UserDataStore
import com.ntncode.restaurantclient.data.sp.SessionSP
import com.ntncode.restaurantclient.databinding.FragmentSignupOauthBinding
import com.ntncode.restaurantclient.model.CustomerData
import com.ntncode.restaurantclient.model.CustomerResponse
import com.ntncode.restaurantclient.model.UserData
import com.ntncode.restaurantclient.util.dialogs.LoadStyleAlertDialog
import com.ntncode.restaurantclient.view.MainActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class SignUpOAFragment : Fragment() {
    //[MAIN]
    private var _binding: FragmentSignupOauthBinding? = null
    private val binding get() = _binding!!

    //[FIREBASE]
    var auth: FirebaseAuth? = null


    //[ELEMENTS VIEW]
    private var et_firstname: EditText? = null
    private var et_lastname: EditText? = null
    private var et_phone: EditText? = null
    private var et_mail: EditText? = null


    private lateinit var dialog: LoadStyleAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initBind() {

        et_firstname = _binding?.etFirstnameSignupOa
        et_lastname = _binding?.etLastnameSignupOa
        et_phone = _binding?.etPhoneSignupOa
        et_mail = _binding?.etEmailSignupOa


    }

    private fun initRes() {

        auth = FirebaseAuth.getInstance()

    }

    private fun initEvents() {


        _binding?.btnSignupOa?.setOnClickListener {
            validateInputs()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignupOauthBinding.inflate(inflater, container, false)

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
        initEvents()

        if (arguments != null) {
            numberPhone = arguments?.getString("numberPhone")
            if (!numberPhone.isNullOrEmpty()) et_phone?.setText(numberPhone) else et_mail?.setText("No cargado")
        } else {
            Toast.makeText(requireContext(), "Valores insuficientes", Toast.LENGTH_SHORT).show()
        }
    }


    private fun validateInputs() {

        if (et_firstname?.text?.toString().isNullOrEmpty()) {

            et_firstname?.error = "Ingrese sus nombres"

        } else if (et_lastname?.text?.toString().isNullOrEmpty()) {

            et_lastname?.error = "Ingrese sus apellidos"

        } else if (et_mail?.text?.toString().isNullOrEmpty()) {

            et_mail?.error = "Ingrese su correo"

        } else if (!(et_mail?.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()))) {

            et_mail?.error = "Correo inválido"
        } else {
            registerDialog()
        }

    }

    private fun registerDialog() {

        dialog = LoadStyleAlertDialog(requireContext(), false).apply {
            set(
                message = getString(R.string.message_register_dialog)
            )
        }
        dialog.show()
        registerCustomer()
    }


    private fun registerCustomer() {

        val call = RetrofitClient.serviceApiUser.registerCustomer(
            auth?.uid.toString(),
            et_firstname?.text.toString().trim(),
            et_lastname?.text.toString().trim(),
            numberPhone?.toInt(),
            et_mail?.text.toString().trim(),
            "no inserted"
        )

        call.enqueue(object : retrofit2.Callback<List<CustomerResponse>> {
            override fun onResponse(
                call: Call<List<CustomerResponse>>,
                response: Response<List<CustomerResponse>>
            ) {
                if (response.isSuccessful) {
                    val result: List<CustomerResponse> = response.body()!!

                    Log.e("LOG_LISTITEM", "LOG RESULT: $result")
                    if (response.code() == 200) {
                        Toast.makeText(
                            requireContext(),
                            result.get(0).message_server,
                            Toast.LENGTH_SHORT
                        ).show()

                        if (result[0].code_server == 200){
                            retrieveData()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<CustomerResponse>>, t: Throwable) {

                Log.e("LOG_LISTITEM", "LOG RESULT:\n .${t.message} \nss")
                dialog.dismiss()
            }
        })

    }

    private fun retrieveData() {
        val uid: String = auth?.uid.toString()

        val ds = UserData(
            firstName = et_firstname?.text.toString(),
            lastName = et_lastname?.text.toString(),
            phone = numberPhone.toString(),
            uid = uid
        )

        val dataStore = UserDataStore(requireContext())

        lifecycleScope.launch {
            dataStore.setData(ds)
            saveState()
        }
    }

    private fun saveState() {
        val sharedPreference = SessionSP(requireContext())
        val stateSession = sharedPreference.getStateSession()

        if (stateSession.equals(getString(R.string.status_denied)))
            sharedPreference.setStateSession(getString(R.string.status_allowed))
        navigateToHome()
    }

    private fun navigateToHome() {

        dialog.dismiss()

        //startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    /*
    * -------------------------------------------------------------------------
     */

    companion object {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        var numberPhone: String? = ""
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.e("LOG_DESTROYVIEW", "STATE: destroy view")
    }
}