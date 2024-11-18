package com.example.ptamanah.view.marketing

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentAnalisisBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.taosif7.android.ringchartlib.RingChart
import com.taosif7.android.ringchartlib.models.RingChartData

class AnalisisFragment : Fragment() {

    private var _binding: FragmentAnalisisBinding? = null
    private val binding: FragmentAnalisisBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalisisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLineChart()
        setupRingChart()
    }

    private fun setupLineChart() {
        // Data untuk setiap line
        val salesEntries = ArrayList<Entry>()
        val ordersEntries = ArrayList<Entry>()
        val visitorsEntries = ArrayList<Entry>()
        val viewsEntries = ArrayList<Entry>()

        for (i in 0 until 5) {
            salesEntries.add(Entry(i.toFloat(), (10 + i * 5).toFloat())) // Penjualan
            ordersEntries.add(Entry(i.toFloat(), (8 + i * 3).toFloat())) // Pesanan
            visitorsEntries.add(Entry(i.toFloat(), (15 + i * 2).toFloat())) // Total Pengunjung
            viewsEntries.add(Entry(i.toFloat(), (12 + i * 4).toFloat())) // Produk Dilihat
        }

        // Membuat LineDataSet untuk setiap line
        val salesDataSet = LineDataSet(salesEntries, "Penjualan").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircles(true) // Menampilkan titik data
            setDrawFilled(true) // Mengisi area di bawah garis
            fillColor = Color.BLUE // Warna isi
            fillAlpha = 50 // Transparansi isi
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER // Menggunakan kurva
        }

        val ordersDataSet = LineDataSet(ordersEntries, "Pesanan").apply {
            color = Color.YELLOW
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircles(true)
            setDrawFilled(true)
            fillColor = Color.YELLOW
            fillAlpha = 50
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        }

        val visitorsDataSet = LineDataSet(visitorsEntries, "Total Pengunjung").apply {
            color = Color.GREEN
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircles(true)
            setDrawFilled(true)
            fillColor = Color.GREEN
            fillAlpha = 50
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        }

        val viewsDataSet = LineDataSet(viewsEntries, "Produk Dilihat").apply {
            color = Color.MAGENTA // Pink
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircles(true)
            setDrawFilled(true)
            fillColor = Color.MAGENTA
            fillAlpha = 50
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        }

        // Menggabungkan semua dataset
        val lineData = LineData(salesDataSet, ordersDataSet, visitorsDataSet, viewsDataSet)

        // Set data ke chart
        binding.lineChart.data = lineData
        binding.lineChart.invalidate() // Refresh chart

        // Mengatur legend
        binding.lineChart.legend.isEnabled = true // Enable legend

        binding.lineChart.xAxis.apply {
            setDrawGridLines(false)
            position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
            valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return "${value.toInt()}:00" // Format jam
                }
            }
        }

        binding.lineChart.axisLeft.isEnabled = false
        binding.lineChart.axisRight.isEnabled = false // Disable right axis
    }

    private fun setupRingChart() {
        val dataPoints = HashMap<String, Float>().apply {
            put("Pembeli Baru", 0.30f)
            put("Pembeli Reguler", 0.45f)
            put("Total Pembeli", 1.0f)
        }

        val colors = HashMap<String, Int>().apply {
            put("Pembeli Baru", ContextCompat.getColor(requireContext(), R.color.blue_500))
            put("Pembeli Reguler", Color.parseColor("#FFA500"))
            put("Total Pembeli", Color.parseColor("#4CAF50"))
        }

        val data = ArrayList<RingChartData>().apply {
            for (label in dataPoints.keys) {
                add(RingChartData(dataPoints[label]!!, colors[label]!!, label))
            }
        }

        binding.ringChart.apply {
            setLayoutMode(RingChart.renderMode.MODE_CONCENTRIC)
            setData(data)
            showLabels(false) // Menyembunyikan label
            startAnimateLoading()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            binding.ringChart.stopAnimateLoading()
        }, 3000)
    }

    override fun onDestroyView() {
        binding.ringChart.stopAnimateLoading()
        super.onDestroyView()
        _binding = null
    }

}