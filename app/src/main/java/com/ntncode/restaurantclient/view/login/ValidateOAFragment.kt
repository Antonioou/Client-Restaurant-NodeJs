package com.ntncode.restaurantclient.view.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.datastore.UserDataStore
import com.ntncode.restaurantclient.data.sp.SessionSP
import com.ntncode.restaurantclient.databinding.FragmentValidateOauthBinding
import com.ntncode.restaurantclient.model.CustomerData
import com.ntncode.restaurantclient.model.CustomerResponse
import com.ntncode.restaurantclient.model.UserData
import com.ntncode.restaurantclient.util.dialogs.LoadStyleAlertDialog
import com.ntncode.restaurantclient.view.MainActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ValidateOAFragment : Fragment() {

    //[MAIN]
    private var _binding: FragmentValidateOauthBinding? = null
    private val binding get() = _binding!!

    //[Firebase]
    private lateinit var auth: FirebaseAuth

    //[DATASTORE]
    private lateinit var dataStoreUserManager: UserDataStore

    //[VARIABLE LOCAL]
    private var storedVerificationId: String? = ""
    private var numberPhone: String? = ""

    //
    private lateinit var dialogValidateData: LoadStyleAlertDialog

    private fun initBind() {

    }

    private fun initRes() {

        //RetrofitClient.interceptor.level = HttpLoggingInterceptor.Level.HEADERS

        auth = FirebaseAuth.getInstance()
        dataStoreUserManager = UserDataStore(requireContext())

    }

    private fun initEvent() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentValidateOauthBinding.inflate(inflater, container, false)

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
        initEvent()

        if (arguments != null) {
            storedVerificationId = arguments?.getString("verificationId")
            numberPhone = arguments?.getString("numberPhone")
        } else {
            Toast.makeText(requireContext(), "Nulo arg", Toast.LENGTH_SHORT).show()
        }

        _binding?.fabBack?.setOnClickListener {
            findNavController().navigate(R.id.action_ValidateOAFragment_to_LoginOAFragment2)
        }

        _binding?.btnValidateOauth?.setOnClickListener {
            verifyCode()
        }

        verificationOTP()
    }

    private fun verificationOTP() {

        _binding?.idEnterCode?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (_binding!!.idEnterCode.text.isEmpty()) {
                    setProgressAnimate(_binding!!.enterProgressBar, 0)
                } else if (_binding!!.idEnterCode.text.length == 1) {
                    setProgressAnimate(_binding!!.enterProgressBar, 10)
                } else if (_binding!!.idEnterCode.text.length == 2) {
                    setProgressAnimate(_binding!!.enterProgressBar, 20)
                } else if (_binding!!.idEnterCode.text.length == 3) {
                    setProgressAnimate(_binding!!.enterProgressBar, 30)
                } else if (_binding!!.idEnterCode.text.length == 4) {
                    setProgressAnimate(_binding!!.enterProgressBar, 40)
                } else if (_binding!!.idEnterCode.text.length == 5) {
                    setProgressAnimate(_binding!!.enterProgressBar, 50)
                } else if (_binding!!.idEnterCode.text.length == 6) {
                    setProgressAnimate(_binding!!.enterProgressBar, 60)
                }
            }
        })
    }

    fun setProgressAnimate(pb: ProgressBar, progressTo: Int) {
        val animation: ObjectAnimator = ObjectAnimator.ofInt(
            pb, "progress", pb.progress, progressTo
        )
        animation.duration = 200
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    private fun verifyCode() {
        val code: String?
        code = _binding?.idEnterCode!!.text.toString().trim()

        if (code.length == 6) {
            if (storedVerificationId?.isNotEmpty() == true)
                verifyPhoneNumberWithCode(storedVerificationId, code)
            _binding?.fabBack?.isEnabled = false
            _binding?.idEnterCode?.isEnabled = false
            _binding?.btnValidateOauth?.isEnabled = false

        } else {
            Toast.makeText(requireContext(), "Ingrese el código.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    //Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    val currentUser: String? = auth.uid

                    /*lifecycleScope.launch {
                        dataStoreUserManager.setPhoneNumber("99992222")
                    }*/

                    validateDialog()


                } else {

                    //Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(requireContext(), "Código incorrecto.", Toast.LENGTH_SHORT)
                            .show()
                    }

                    _binding?.fabBack?.isEnabled = true
                    _binding?.idEnterCode?.isEnabled = true
                    _binding?.btnValidateOauth?.isEnabled = true
                    _binding?.idEnterCode?.setText("")
                    setProgressAnimate(_binding!!.enterProgressBar, 0)

                }
            }
    }

    private fun validateDialog() {

        dialogValidateData = LoadStyleAlertDialog(
            requireContext(),
            false
        ).apply {
            set(
                message = getString(R.string.message_validate_dialog)
            )
        }
        dialogValidateData.show()
        validateExistStore()

    }


    private fun validateExistStore() {

        val uid: String?
        uid = auth.uid.toString()

        if (!uid.isNullOrEmpty()) {

            val call = RetrofitClient.serviceApiUser.findOneCustomerByUID(uid)
            call.enqueue(object : retrofit2.Callback<List<CustomerResponse>> {
                override fun onResponse(
                    call: Call<List<CustomerResponse>>,
                    response: Response<List<CustomerResponse>>
                ) {
                    if (response.isSuccessful) {
                        val result: List<CustomerResponse> = response.body()!!


                        val state_msg: String = result.get(0).message_server
                        var code_server: Int = result.get(0).code_server
                        //val code_status: String = result.get(0).code_status

                        //val res: CustomerData = result.get(0).response

                        //Log.e("LOG_VALIDATE", "LOG $code_server")
                        when (code_server) {
                            200 -> {
                                Toast.makeText(requireContext(), state_msg, Toast.LENGTH_SHORT)
                                    .show()
                                retrieveData(result[0].response)
                            }
                            401, 404 -> {
                                //Log.e("LOG_VALIDATE", "LOG 401")
                                Toast.makeText(requireContext(), state_msg, Toast.LENGTH_SHORT)
                                    .show()
                                navigateToSignUp()
                            }
                            500 -> {
                                Toast.makeText(requireContext(), state_msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else -> {
                                Toast.makeText(requireContext(), state_msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        /*_binding?.fabBack?.isEnabled = true
                    _binding?.idEnterCode?.isEnabled = true
                    _binding?.btnValidateOauth?.isEnabled = true
                    _binding?.idEnterCode?.setText("")
                    setProgressAnimate(_binding!!.enterProgressBar, 0)*/

                        //Log.e("LOG_VALIDATE", "LOG $res \n${auth.uid}")

                    }
                }

                override fun onFailure(call: Call<List<CustomerResponse>>, t: Throwable) {
                    dialogValidateData.dismiss()
                    Log.e("LOG_VALIDATE", "LOG RESULT:\n .${t.message} \nss")
                }
            })
        }
    }


    /*
    * IF NO THERE IS CUSTOMER
     */

    private fun navigateToSignUp() {

        dialogValidateData.dismiss()

        val number = numberPhone
        val bundle = bundleOf("numberPhone" to number)
        findNavController().navigate(R.id.action_ValidateOAFragment_to_signUpFragment, bundle)
        Log.e("LOG_VALIDATE", "LOG naviToSu")
    }

    /*
    *------------------------------------------------------------
     */

    /*
    * IF THERE IS CUSTOMER
     */

    private fun retrieveData(list: CustomerData) {
        val uid: String = auth.uid.toString()

        val ds = UserData(
            firstName = list.firstname_customer,
            lastName = list.lastname_customer,
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
        dialogValidateData.dismiss()
        //startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    /*
    *------------------------------------------------------------
     */


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.e("LOG_DESTROYVIEW", "STATE: destroy view")
    }


    companion object {
        private const val TAG = "ValidateOAFragment"
    }

}