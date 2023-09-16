package com.example.kotlinpracticemirea

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

import com.example.kotlinpracticemirea.databinding.FragmentFractionInventoryBinding


class FractionInventoryFragment : Fragment() {
    private var _binding: FragmentFractionInventoryBinding? = null
    private val binding get() = _binding!!
    private var _handler: Handler? = null
    private val handler get() = _handler!!
    private val runnable = Runnable {
        binding.viewpagerGear.currentItem = binding.viewpagerGear.currentItem + 1
    }
    private val args by navArgs<FractionInventoryFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFractionInventoryBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        recyclerViewInit()
        val fraction = if (args.fraction) EFTFraction.PMC_USEC else EFTFraction.PMC_BEAR
        uiInit(fraction)


        binding.imageView.setOnClickListener {
            val action = FractionInventoryFragmentDirections.actionFractionInventoryFragmentToNotesFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun uiInit(fraction: EFTFraction) {
        if (fraction == EFTFraction.PMC_USEC) {
            binding.header.text = "Welcome to USEC PMC"
            binding.fractionImage.setImageResource(R.drawable.usec_fraction_img)
            binding.specialGearTV.text = "Your USEC equipment:"
        }
        viewPagerInit(fraction)

    }

    private fun viewPagerInit(fraction: EFTFraction) {
        _handler = Handler(Looper.myLooper()!!)
        var itemsList = listOf<String>()
        val imageList = ArrayList<Int>()
        val itemsIcons = listOf(
            R.drawable.knife_icon_w ,
            R.drawable.tactical_rig_icon_w ,
            R.drawable.tactical_rig_icon_w ,
            R.drawable.tactical_rig_icon_w ,
            R.drawable.tactical_rig_icon_w ,
            R.drawable.weapon_icon_w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.weapon_icon_w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.weapon_icon_w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.food_icon_w ,
            R.drawable.food_icon_w
        )

        if (fraction == EFTFraction.PMC_BEAR) {
            itemsList = listOf(
                "Knife (6h5 Bayonet)" ,
                "Triton M43-A chest harness (3 pcs.)" ,
                "Wartech Eagle VV-102 backpack (3 pcs.)" ,
                "BEAR baseball cap (Black) (3 pcs.)" ,
                "GSSh-01 active headphones (2 pcs.)" ,
                "PP-19-01 \"Vityaz-SN\" 9x19 submachine gun (2 pcs.)" ,
                "30-round 9x19 PP-19-01 magazine (6 pcs.)" ,
                "MP-443 \"Grach\" 9x19 pistol (2 pcs.)" ,
                "18-round 9x19 MP-443 magazine (9 pcs.)" ,
                "AK-74M 5.45x39 assault rifle (2 pcs.)" ,
                "30-round 5.45x39 6L23 magazine for AK-74 and compatibles (7 pcs.)" ,
                "F-1 grenade (2 pcs.)" ,
                "5.45x39mm PS gs cartridges (120 pcs.)" ,
                "Iskra MRE (4 pcs.)" ,
                "Bottle of Tarkovskaya vodka"
            )
            imageList.add(R.drawable.knife_6h5_bayonett)
            imageList.add(R.drawable.triton_tactical_rig)
            imageList.add(R.drawable.berkut_backpack)
            imageList.add(R.drawable.bear_cap)
            imageList.add(R.drawable.gshh_headphones)
            imageList.add(R.drawable.vityaz_smg)
            imageList.add(R.drawable.vityaz_smg_mag)
            imageList.add(R.drawable.grach_pistol)
            imageList.add(R.drawable.grach_mag)
            imageList.add(R.drawable.ak74)
            imageList.add(R.drawable.ak_74m_mag)
            imageList.add(R.drawable.f_1_grenade)
            imageList.add(R.drawable.ps_rounds)
            imageList.add(R.drawable.iskra_mre)
            imageList.add(R.drawable.vodka)

        } else {
            itemsList = listOf(
                "Knife (ER Fulcrum Bayonet)" ,
                "Blackhawk! Commando Chest Harness Black (3 pcs.)" ,
                "LBT-8005A Day Pack backpack (3 pcs.)" ,
                "USEC cap (3 pcs.)" ,
                "Walker's Razor Digital headset (2 pcs.)" ,
                "HK MP5 (2 pcs.)" ,
                "20-round 9x19 MP5 magazine (8 pcs.)" ,
                "M9A3 9x19 pistol (2 pcs.)" ,
                "17-round 9x19 M9A3 magazine (9 pcs.)" ,
                "Colt M4A1 5.56x45 assault rifle (2 pcs.)" ,
                "30-round 5.56x45 Colt AR-15 STANAG magazine (7 pcs.)" ,
                "M67 Grenade (2 pcs.)" ,
                "5.56x45mm M855 (180 pcs.)" ,
                "MRE (4 pcs.)" ,
                "Bottle of Dan Jackiel Whiskey"
            )
            imageList.add(R.drawable.img)
            imageList.add(R.drawable.img_1)
            imageList.add(R.drawable.img_2)
            imageList.add(R.drawable.img_3)
            imageList.add(R.drawable.img_4)
            imageList.add(R.drawable.img_5)
            imageList.add(R.drawable.img_6)
            imageList.add(R.drawable.img_7)
            imageList.add(R.drawable.img_8)
            imageList.add(R.drawable.img_9)
            imageList.add(R.drawable.img_10)
            imageList.add(R.drawable.img_11)
            imageList.add(R.drawable.img_12)
            imageList.add(R.drawable.img_13)
            imageList.add(R.drawable.img_14)

        }


        val adapter = ImageAdapter(imageList , binding.viewpagerGear)
        binding.viewpagerGear.adapter = adapter
        binding.viewpagerGear.adapter = adapter
        binding.viewpagerGear.offscreenPageLimit = 3
        binding.viewpagerGear.clipChildren = false
        binding.viewpagerGear.clipToPadding = false
        binding.viewpagerGear.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS

        setUpTransformer()
        binding.viewpagerGear.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 3000)

                binding.itemIcon.setImageResource(itemsIcons[binding.viewpagerGear.currentItem % itemsIcons.size])
                binding.itemName.text =
                    itemsList[binding.viewpagerGear.currentItem % itemsIcons.size]


            }
        })
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page , position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f

        }
        binding.viewpagerGear.setPageTransformer(transformer)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable , 2000)
    }

    private fun recyclerViewInit() {
        val itemsList = listOf(
            "Secure container Alpha (2х2.)" ,
            "PACA soft armor (3 pcs.)" ,
            "9x19mm Pst gzh (425 pcs.)" ,
            "Zarya stun grenade (2 pcs.)" ,
            "Can of beef stew (2 pcs.)" ,
            "Water bottle 0.6 l. (4 pcs.)" ,
            "AI-2 kit (3 pcs.)" ,
            "Aseptic bandage (3 pcs.)" ,
            "Analgin painkillers (3 pcs.)" ,
            "Immobilizing splint (4 pcs.)" ,
            "Esmarch tourniquet (2 pcs.)" ,
            "Car first-aid kit (2 pcs.)" ,
            "Vaseline" ,
            "Expeditionary fuel tank" ,
            "Peltor ComTac 2 headset" ,
            "Army bandage" ,
            "Ripstop fabric" ,
            "Fleece fabric" ,
            "500 000 roubles"
        )
        val iconsList = listOf(
            R.drawable.tactical_rig_icon_w ,
            R.drawable.armor_icon_w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.icon_ammunition__w ,
            R.drawable.food_icon_w ,
            R.drawable.food_icon_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.material_icon_w ,
            R.drawable.tactical_rig_icon_w ,
            R.drawable.icon_plus_sign_w ,
            R.drawable.material_icon_w ,
            R.drawable.material_icon_w ,
            R.drawable.money_icon_w
        )
        val adapter = EquipmentRecyclerViewAdapter(itemsList , iconsList)
        binding.itemsRV.adapter = adapter
        val layoutManager = object : LinearLayoutManager(context , RecyclerView.VERTICAL , false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        binding.itemsRV.layoutManager = layoutManager
    }


}