package com.mahrous.advashowimagetask.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mahrous.advashowimagetask.R
import com.mahrous.advashowimagetask.databinding.FragmenDisplayImageBinding
import com.mahrous.advashowimagetask.setBindImage

class DisplayImageFragment : Fragment() {
    var mScaleFactor = 1.0f
    lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var binding: FragmenDisplayImageBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scaleGestureDetector= ScaleGestureDetector(requireContext(),ScaleListener())
        binding.displayedImage.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                scaleGestureDetector.onTouchEvent(event)
                return true
            }

        })
        binding.imageUrl = requireArguments()["url"] as String
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragmen_display_image, container, false)
        return binding.root
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f))
            binding.displayedImage.scaleX = (mScaleFactor)
            binding.displayedImage.scaleY = (mScaleFactor)
            return true
        }
    }
}