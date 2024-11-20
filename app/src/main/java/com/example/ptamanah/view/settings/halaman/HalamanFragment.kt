package com.example.ptamanah.view.settings.halaman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentHalamanBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel


class HalamanFragment : Fragment() {
    private var _binding: FragmentHalamanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHalamanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val previewButton = view.findViewById<Button>(R.id.previewBtn)
        previewButton.setOnClickListener {
            navigateToPreviewFragment()
        }
        initCard()
        initExpandableComponent()
    }

    private fun initCard() {
        val radiusInPixels = resources.getDimension(R.dimen.card_corner_radius)
        setTopRoundedCorners(binding.cardToko, radiusInPixels, 0f)
        setTopRoundedCorners(binding.cardMediaSosial, 0f, 0f)
        setTopRoundedCorners(binding.cardTransaksi, 0f, 0f)
        setTopRoundedCorners(binding.cardButton, 0f, radiusInPixels)
    }

    private fun navigateToPreviewFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, PreviewFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initExpandableComponent() {
        binding.containerToko.setOnClickListener {
            if (binding.contentToko.visibility == View.GONE) {
                binding.contentToko.visibility = View.VISIBLE
                binding.arrowToko.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
            } else {
                binding.contentToko.visibility = View.GONE
                binding.arrowToko.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
            }
        }
        binding.containerMediaSosial.setOnClickListener {
            if (binding.contentMediaSosial.visibility == View.GONE) {
                binding.contentMediaSosial.visibility = View.VISIBLE
                binding.arrowMediaSosial.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
            } else {
                binding.contentMediaSosial.visibility = View.GONE
                binding.arrowMediaSosial.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
            }
        }
        binding.containerTransaksi.setOnClickListener {
            if (binding.contentTransaksi.visibility == View.GONE) {
                binding.contentTransaksi.visibility = View.VISIBLE
                binding.arrowTransaksi.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
            } else {
                binding.contentTransaksi.visibility = View.GONE
                binding.arrowTransaksi.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
            }
        }
    }

    private fun setTopRoundedCorners(cardView: MaterialCardView, radiusTop: Float, radiusBottom: Float) {
        val shapeAppearanceModel = ShapeAppearanceModel.Builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, radiusTop)
            .setTopRightCorner(CornerFamily.ROUNDED, radiusTop)
            .setBottomLeftCorner(CornerFamily.ROUNDED, radiusBottom)
            .setBottomRightCorner(CornerFamily.ROUNDED, radiusBottom)
            .build()

        cardView.shapeAppearanceModel = shapeAppearanceModel
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}