package com.ntncode.restaurantclient.util


/*binding.buttonFirst.setOnClickListener {
    findNavController().navigate(R.id.action_HomeFragment_to_ProfileCustomerFragment2)
}*/


/* dataStoreUserManager.getPhoneNumber.asLiveData().observe(requireActivity(), {
        if (it != null) {
            Log.e("LOG_LISTITEM", "LOG RESULT: $it")
        }
    })
*/
/*
lifecycleScope.launch {
    name = dataStoreUserManager.getPhoneNumber()
    Log.e("LOG_LISTITEM", "LOG RESULT: $name")
}*/

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

private fun setImage() {
        var shareTarget: Target?
        shareTarget = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {

                binding.ivImageItemDetail.setImageBitmap(bitmap)
                Palette.from(bitmap)
                    .generate {

                        if (it != null) {
                            it.darkVibrantSwatch?.rgb?.let { it1 ->
                                binding.mcvContentItemDetail.setCardBackgroundColor(
                                    it1
                                )
                            }

                            it.vibrantSwatch?.bodyTextColor?.let { it1 ->
                                binding.tvNameItemDetail.setTextColor(
                                    it1
                                )
                            }
                        }
                    }


                //shareTarget = null

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                // don't need to store it any longer
                shareTarget = null
            }
        }


        Picasso.get()
            .load("https://source.unsplash.com/random")
            //.centerCrop()
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(shareTarget as Target)
    }

 */

