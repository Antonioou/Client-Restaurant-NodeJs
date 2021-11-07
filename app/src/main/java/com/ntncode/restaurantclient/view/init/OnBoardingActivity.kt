package com.ntncode.restaurantclient.view.init

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.adapter.OnBoardingPagerAdapter
import com.ntncode.restaurantclient.model.OnBoardingData
import com.ntncode.restaurantclient.data.datastore.LoginDataStore
import com.ntncode.restaurantclient.data.sp.SessionSP
import com.ntncode.restaurantclient.view.MainActivity
import kotlinx.coroutines.launch

class OnBoardingActivity : AppCompatActivity() {

    //[DATA STORE]
    //private lateinit var loginDS: LoginDataStore

    //[ELEMENTS]
    private lateinit var viewPager2: ViewPager2

    //[VARIABLES]
    //var state_login: String = ""



    //https://kotlincodes.com/kotlin/viewpager-in-kotlin-android/

    //https://chjune0205.tistory.com/entry/%EC%84%B8%EB%A1%9C-
    // %EB%B0%A9%ED%96%A5-%EB%B7%B0%ED%8E%98%EC%9D%B4%EC%A0%80-%EC%A0%81%EC%9A%A9%E
    // D%95%98%EA%B8%B0-Vertical-ViewPager-Kotlin?category=900789?category=900789

    //https://intensecoder.com/android-swipe-views-using-viewpager2-tutorial-in-kotlin/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)


        //loginDS = LoginDataStore(this.applicationContext)

        validateOBStatus()

        /*lifecycleScope.launch {
            var a = loginDS.getStateNormal()
            if (a != null) {
                validateLoginStatus(a)
            }
        }

        loginDS.getState.asLiveData().observe(this, {
            if (it != null) {
                validateLogin(it)
            }
        })*/


        /*val viewPager = findViewById<ViewPager>(R.id.viewPager_onBoarding)
        viewPager.adapter = OnBoardingPagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)*/

        val window: Window = window
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        val wic = WindowCompat.getInsetsController(window, window.decorView)
        wic?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        wic?.isAppearanceLightStatusBars = true
        window.statusBarColor = Color.TRANSPARENT


        val list: MutableList<OnBoardingData> = mutableListOf()
        val data_1 =
            OnBoardingData("Servicio de calidad", "Ofrecemos un servicio rapido y eficiente a tu disposición",
                "ssimg",  R.drawable.ic_baseline_chevron_right_24, "Siguiente")
        val data_2 =
            OnBoardingData("Facil e intuitivo", "Te brindamos un servicio sencillo y accesible ante tus necesidades",
                "ssimg",  R.drawable.ic_baseline_chevron_right_24, "Siguiente")
        val data_3 =
            OnBoardingData("sdas3", "sdasmess3",
                "ssimg", R.drawable.ic_baseline_done_24, "Finalizar")

        list.add(data_1)
        list.add(data_2)
        list.add(data_3)

        val adapter = OnBoardingPagerAdapter(list)
        viewPager2 = findViewById(R.id.viewPager)
        viewPager2.adapter = adapter

    }

    private fun validateOBStatus() {

        val sharedPreference = SessionSP(this)

        var state_session = sharedPreference.getStateOnBoarding()

        //Toast.makeText(applicationContext, "status: $state_login", Toast.LENGTH_SHORT).show()

        if (state_session.equals(getString(R.string.status_ob_yes))) {

            Log.e("log_states_session", "ONBOARDING")

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()

        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}