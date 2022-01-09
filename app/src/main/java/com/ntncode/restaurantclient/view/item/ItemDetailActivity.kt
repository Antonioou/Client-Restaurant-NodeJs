package com.ntncode.restaurantclient.view.item

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.palette.graphics.Palette
import com.ntncode.restaurantclient.api.RetrofitClient
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel
import com.ntncode.restaurantclient.data.roomdb.repository.CartRepository
import com.ntncode.restaurantclient.databinding.ActivityItemDetailBinding
import com.ntncode.restaurantclient.model.ItemResponse
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import retrofit2.Call
import retrofit2.Response


class ItemDetailActivity : AppCompatActivity() {

    //***[MAIN]
    private lateinit var binding: ActivityItemDetailBinding

    //***[DATA STORE]
    //***[VARIABLES]

    //***[ROOM DATABASE]
    var cartEntity: CartEntityModel? = null


    companion object {
        var idItemParam: String? = ""
        var nameItemParam: String? = ""
        var imageItemParam: String? = ""
        var costItemParam: String? = ""

        var resultListItem: List<ItemResponse>? = null

        private const val qtyDefItem = 1
        var countTemp = ""
        var globalMinQuantity: String = "1"
        var globalMaxQuantity: String = "12"
    }


    private fun initEvents() {
        binding.fabBack.setOnClickListener {
            finish()
        }

        binding.fabNegativeDetailProduct.setOnClickListener {
            restQtyItem()
        }
        binding.fabPositiveDetailproduct.setOnClickListener {
            sumQtyItem()
            //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

        binding.btnAddCartItemDetail.setOnClickListener {
            addToCartRoom()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this.window
        val wic = WindowCompat.getInsetsController(window, window.decorView)
        wic?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        wic?.isAppearanceLightStatusBars = true
        window.statusBarColor = Color.TRANSPARENT


        initEvents()

        if (intent.extras?.isEmpty == false) {
            val idLocal = intent.getStringExtra("idItem")
            val imageLocal = intent.getStringExtra("imageItem")
            val nameLocal = intent.getStringExtra("nameItem")
            val costLocal = intent.getStringExtra("costItem")
            if (!idLocal.isNullOrEmpty()) {
                idItemParam = idLocal
                imageItemParam = imageLocal
                nameItemParam = nameLocal
                costItemParam = costLocal
                setData()
                setImage()
                //getItemData(idLocal)
                Toast.makeText(this, idItemParam.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        binding.tvNameItemDetail.text = nameItemParam
        binding.tvCostItemDetail.text = "S/. $costItemParam"
    }

    private fun setImage() {
        var shareTarget: Target?
        shareTarget = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {

                binding.ivImageItemDetail.setImageBitmap(bitmap)
                Palette.from(bitmap)
                    .generate {
                        if (it != null) {

                            it.darkMutedSwatch?.rgb?.let { it1 ->
                                binding.mcvContentItemDetail.setCardBackgroundColor(
                                    it1
                                )
                                binding.mcvImageItemDetail.setCardBackgroundColor(
                                    it1
                                )
                            }
                            it.darkVibrantSwatch?.rgb?.let { it1 ->
                                binding.btnAddCartItemDetail.backgroundTintList =
                                    ColorStateList.valueOf(it1)
                            }

                        }
                    }
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                // don't need to store it any longer
                shareTarget = null
            }
        }
        Picasso.get()
            .load(imageItemParam)
            //.centerCrop()
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(shareTarget as Target)
    }


    private fun getItemData(id: String) {
        val call = RetrofitClient.serviceApiUser.getItemDetailByID(id)
        call.enqueue(object : retrofit2.Callback<List<ItemResponse>> {
            override fun onResponse(
                call: Call<List<ItemResponse>>,
                response: Response<List<ItemResponse>>
            ) {
                if (response.isSuccessful) {
                    resultListItem = response.body()!!

                    val msg: String = resultListItem!![0].message_server
                    val code: Int = resultListItem!![0].code_server
                    val desc = resultListItem!![0].response.description_item

                    if (response.code() == 200) {

                        when (code) {
                            200 -> {
                                if (desc.isNotEmpty()) binding.tvDescItemDetail.text =
                                    desc else binding.tvDescItemDetail.visibility = View.GONE
                            }
                            401 -> Toast.makeText(this@ItemDetailActivity, msg, Toast.LENGTH_SHORT)
                                .show()
                            500 -> Toast.makeText(this@ItemDetailActivity, msg, Toast.LENGTH_SHORT)
                                .show()
                        }

                        Log.e("LOG_LISTITEM", "LOG RESULT: $resultListItem")

                    }
                }
            }

            override fun onFailure(call: Call<List<ItemResponse>>, t: Throwable) {
                Log.e("LOG_LISTITEM", "LOG RESULT:\n .${t.message} \nss")
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun sumQtyItem() {

        if (globalMaxQuantity.isNotEmpty()) {
            val maxLocal: Int = globalMaxQuantity.toInt()
            var tvQty: Int = binding.tvQtyAddDetailProduct.text.toString().toInt()

            val countTemporal = if (countTemp.isEmpty()) {
                0
            } else {
                countTemp.toInt()
            }


            val total: Int = tvQty + countTemporal
            var price = 0.0

            val pricel = 12.0

            if (pricel != 0.0) {
                price = pricel
            }

            //Toast.makeText(this, ""+total, Toast.LENGTH_SHORT).show()

            if (total >= maxLocal) {
                val textToast = "Cantidad máxima ($maxLocal) alcanzada."
                Toast.makeText(this@ItemDetailActivity, "" + textToast, Toast.LENGTH_SHORT)
                    .show()
            } else {

                tvQty += qtyDefItem

                //Toast.makeText(this, ""+tvQty, Toast.LENGTH_SHORT).show()

                binding.tvQtyAddDetailProduct.text = tvQty.toString()

                if (price != 0.0) {
                    val sumPrice: Double = price * tvQty
                    binding.tvCostItemDetail.text = "S/. " + String.format("%.2f", sumPrice)
                }


            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun restQtyItem() {
        if (globalMinQuantity.isNotEmpty()) {
            val minLocal: Int = globalMinQuantity.toInt()
            var count: Int = binding.tvQtyAddDetailProduct.text.toString().toInt()
            var price = 0.0

            //val listData: ItemData = resultListItem!![0].response

            val pricel = 12.0

            if (pricel != 0.0) {
                //price = listData.price_detail_item
                price = pricel
            }
            if (count > minLocal) {
                count -= qtyDefItem
                binding.tvQtyAddDetailProduct.text = count.toString()
                val sumPrice = price * count
                binding.tvCostItemDetail.text = "S/. " + String.format("%.2f", sumPrice)
            }
        }
    }


    private fun addToCartRoom() {

        val cart: CartEntityModel? = null

        val cartRepository = CartRepository(this@ItemDetailActivity)

        if (binding.tvCostItemDetail.text.isNotEmpty()
        ) {

            cart?.let{

            } ?: kotlin.run {
                val cart = CartEntityModel(
                    //userId = idItemParam?.toInt(),
                    userName = "name 3",
                    location = "location",
                    email = "https://source.unsplash.com/random"
                )
                cartRepository.insertItemToCart(cart)
                Log.e("LOG_ITEMDETAIL", "ACTION: INSERTADO \n${cart.toString()}")
            }

            /*cartEntity?.apply {
                val cart = CartEntityModel(
                    //userId = it.userId,
                    userName = "name ",
                    location = "location ",
                    email = "email "
                )
                cartRepository.insertItemToCart(cart)
                Log.e("LOG_ITEMDETAIL", "ACTION: INSERTADO $cart")

            }*/



            //Log.e("LOG_ITEMDETAIL", "ACTION: INSERTADO ")



        } else {
            Toast.makeText(
                this@ItemDetailActivity,
                "Datos invalidos!", Toast.LENGTH_SHORT
            ).show()
        }

    }

}