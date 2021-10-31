package com.ntncode.restaurantclient.view.login

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.databinding.FragmentLoginOauthBinding
import java.util.concurrent.TimeUnit

class LoginOAFragment : Fragment() {

    //[MAIN]
    private var _binding: FragmentLoginOauthBinding? = null
    private val binding get() = _binding!!

    //[fIREBASE]
    var auth: FirebaseAuth? = null

    //[VARIABLE METHOD'S]
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    //[VARIABLE LOCAL]
    private var storedVerificationId: String? = ""

    //[ELEMENTS VIEW]
    private var btn_signin: MaterialButton? = null
    private var et_number: EditText? = null


    private fun initBind() {

        btn_signin = _binding?.btnSigninOauth

        et_number = _binding?.etNumberphoneOauth
    }

    private fun initRes() {

        auth = FirebaseAuth.getInstance()

    }

    private fun initEvents() {

        _binding?.fabBack?.setOnClickListener {
            findNavController().navigate(R.id.action_LoginOAFragment_to_startOAFragment)
        }

        btn_signin?.setOnClickListener {
            validatePhone()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginOauthBinding.inflate(inflater, container, false)

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

        initBind()
        initRes()
        initEvents()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                //signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(ContentValues.TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(ContentValues.TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                fragmentNavigation()
            }
        }
    }


    private fun validatePhone() {

        val phone: String = et_number?.text.toString().trim { it <= ' ' }

        if (!TextUtils.isEmpty(phone)) {
            if (phone.length == 9) {

                btn_signin?.setEnabled(false)
                et_number?.setEnabled(false)
                et_number?.setError(null)
                sendVerificationCode("+51$phone")

            } else {
                et_number?.setError("Número erroneo")
                Toast.makeText(requireContext(), "Número de celular incorrecto", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            et_number?.setError("Campo vacio")
            Toast.makeText(
                requireContext(),
                "Por favor ingrese su número de celular",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendVerificationCode(phone: String) {
        val options = auth?.let {
            PhoneAuthOptions.newBuilder(it)
                .setPhoneNumber(phone)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }


    private fun fragmentNavigation() {

        val number = et_number?.text.toString().trim { it <= ' ' }


        val bundle = bundleOf("numberPhone" to number, "verificationId" to storedVerificationId)
        findNavController().navigate(R.id.action_LoginOAFragment_to_ValidateOAFragment, bundle)


        /*findNavController().navigate(LoginOAFragmentDirections.actionLoginOAFragmentToValidateOAFragment()
                .setNumberPhoneLoginArg(number)
                .setVerificationIdLoginArg(storedVerificationId))*/

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val TAG = "LoginOAFragment"
    }
}