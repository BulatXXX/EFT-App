package com.example.kotlinpracticemirea

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinpracticemirea.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _handler: Handler? = null
    private val handler get() = _handler!!
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val runnable = Runnable {
        binding.viewpager.currentItem = binding.viewpager.currentItem + 1
    }

    private var side: EFTFraction? = null


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater , container , false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        initViewPager()
        binding.bearBtn.setOnClickListener {
            side = EFTFraction.PMC_BEAR
            binding.bearBtn.setImageResource(R.drawable.bear_fraction_img_choosen)
            binding.usecBtn.setImageResource(R.drawable.usec_fraction_img)
        }
        binding.usecBtn.setOnClickListener {
            side = EFTFraction.PMC_USEC
            binding.bearBtn.setImageResource(R.drawable.bear_fraction_img)
            binding.usecBtn.setImageResource(R.drawable.usec_fraction_img_choosen)
        }
        binding.saveBtn.setOnClickListener {
            when (side) {
                EFTFraction.PMC_BEAR -> {
                    val action = MainFragmentDirections.actionMainFragmentToBearFragment(fraction = false)
                    Navigation.findNavController(requireView()).navigate(action)
                }
                EFTFraction.PMC_USEC -> {
                    val action = MainFragmentDirections.actionMainFragmentToBearFragment(fraction = true)
                    Navigation.findNavController(requireView()).navigate(action)
                }
                else -> {
                    Toast.makeText(context , "ERROR: CHOOSE A SIDE" , Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun initViewPager() {
        _handler = Handler(Looper.myLooper()!!)
        val imageList = ArrayList<Int>()
        imageList.add(R.drawable.tarkov_img1)
        imageList.add(R.drawable.zryachiy)
        imageList.add(R.drawable.glukhar)
        imageList.add(R.drawable.killa)
        imageList.add(R.drawable.reshala)
        imageList.add(R.drawable.sanitar)
        imageList.add(R.drawable.shturman)
        imageList.add(R.drawable.tagilla)
        imageList.add(R.drawable.tarkov_img2)
        imageList.add(R.drawable.tarkov_img3)

        val adapter = ImageAdapter(imageList , binding.viewpager)

        binding.viewpager.adapter = adapter
        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.clipChildren = false
        binding.viewpager.clipToPadding = false
        binding.viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS

        setUpTransformer()
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 3000)


            }
        })


    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable , 2000)
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page , position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f

        }
        binding.viewpager.setPageTransformer(transformer)
    }


}