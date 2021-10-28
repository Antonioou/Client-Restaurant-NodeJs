package com.ntncode.restaurantclient.view.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.databinding.FragmentLoginOauthBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginOAFragment : Fragment() {

    private var _binding: FragmentLoginOauthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //findNavController().navigate(R.id.action_LoginOAFragment_to_ValidateOAFragment)

    private var btn_signin: MaterialButton? = null


    private fun initBind() {

        btn_signin = _binding?.btnSigninOauth


    }

    private fun initView() {

    }

    private fun initEvents() {

        _binding?.fabBack?.setOnClickListener {
            findNavController().navigate(R.id.action_LoginOAFragment_to_startOAFragment)
        }

        btn_signin?.setOnClickListener {
            findNavController().navigate(R.id.action_LoginOAFragment_to_ValidateOAFragment)
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
        initView()
        initEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}