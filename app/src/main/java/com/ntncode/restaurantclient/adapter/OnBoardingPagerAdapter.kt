package com.ntncode.restaurantclient.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.data.OnBoardingData
import com.ntncode.restaurantclient.view.MainActivity
import com.ntncode.restaurantclient.view.login.OAuthActivity

/*class OnBoardingPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return OneOBFragment()
            }
            1 -> {
                return TwoOBFragment()
            }
            2 -> {
                return ThreeOBFragment()
            }
            else -> {
                return OneOBFragment()
            }
        }
    }

    /*override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Tab 1"
            }
            1 -> {
                return "Tab 2"
            }
            2 -> {
                return "Tab 3"
            }
        }
        return super.getPageTitle(position)
    }*/

}
 */

class OnBoardingPagerAdapter(

    private val list: List<OnBoardingData>

) : RecyclerView.Adapter<OnBoardingPagerAdapter.ViewPagerHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager2_ob, parent, false)
        context = parent.context;

        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {

        val title = list[position].title
        val image = list[position].image
        val msg = list[position].message
        val icon = list[position].icon
        var text_icon = list[position].text_icon
        val animFade: Animation =
            TranslateAnimation(0F, 0f, -80f, 0f)
        animFade.duration = 1600
        //animFade.repeatMode = Animation.RESTART
        //animFade.repeatCount = Animation.INFINITE

        holder.bind(title, msg, text_icon, icon, animFade, context, position)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_title = itemView.findViewById<TextView>(R.id.text_title)
        private val tv_msg = itemView.findViewById<TextView>(R.id.text_message)
        private val iv_icon = itemView.findViewById<ExtendedFloatingActionButton>(R.id.feb_next)

        fun bind(
            text_title: String, text_msg: String, text_icon: String, icon: Int,
            animFade: Animation,
            context: Context,
            position: Int
        ) {
            tv_title.text = text_title
            tv_title.startAnimation(animFade)
            tv_msg.text = text_msg
            tv_msg.startAnimation(animFade)
            iv_icon.setIconResource(icon)
            iv_icon.text = text_icon

            if (position.equals(2)) {
                iv_icon.visibility = View.VISIBLE
            }

            iv_icon.setOnClickListener {

                val intent: Intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }

        }
    }
}
