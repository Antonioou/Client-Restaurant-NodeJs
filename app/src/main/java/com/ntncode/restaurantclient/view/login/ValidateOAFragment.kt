package com.ntncode.restaurantclient.view.login

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.databinding.FragmentValidateOauthBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ValidateOAFragment : Fragment() {

    private var _binding: FragmentValidateOauthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentValidateOauthBinding.inflate(inflater, container, false)

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

        _binding?.fabBack?.setOnClickListener {
            findNavController().navigate(R.id.action_ValidateOAFragment_to_LoginOAFragment2)
        }

        _binding?.btnValidateOauth?.setOnClickListener {

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
                    val code: String?
                    code = _binding?.idEnterCode!!.text.toString().trim()
                    Toast.makeText(requireContext(), "" + code, Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    fun setProgressAnimate(pb: ProgressBar, progressTo: Int) {
        val animation: ObjectAnimator = ObjectAnimator.ofInt(
            pb, "progress", pb.progress, progressTo)
        animation.duration = 200
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}