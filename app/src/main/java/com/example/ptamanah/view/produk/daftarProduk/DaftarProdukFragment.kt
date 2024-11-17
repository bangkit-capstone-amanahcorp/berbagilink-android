package com.example.ptamanah.view.produk.daftarProduk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentDaftarProdukBinding

class DaftarProdukFragment : Fragment() {

    private var _binding: FragmentDaftarProdukBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarProdukBinding.inflate(inflater, container, false)


        binding.btnTambahProduk.setOnClickListener {
            navigateToInformasiProdukFragment()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val products = listOf(
//            Product(
//                1,
//                "Samase Official - Badr Kemko",
//                "Barang",
//                "Fashion Pria",
//                289000,
//                50,
//                true,
//                listOf("Edit", "Delete", "View Details")
//            ),
//            Product(
//                2,
//                "Produk Lain",
//                "Barang",
//                "Elektronik",
//                1500000,
//                20,
//                false,
//                listOf("Edit", "Delete", "View Details")
//            ),
//            Product(
//                3,
//                "Coffe Maker",
//                "Barang",
//                "Home Appliances",
//                800000,
//                15,
//                true,
//                listOf("Edit", "Delete", "View Details")
//            ),
//            Product(
//                4,
//                "Gaming Headset",
//                "Barang",
//                "Accessories",
//                1200000,
//                10,
//                true,
//                listOf("Edit", "Delete", "View Details")
//            )
//        )
//
//        populateTable(products)
    }

    private fun navigateToInformasiProdukFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, InformasiProdukFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

//    private fun populateTable(products: List<Product>) {
//        binding.tableLayout.removeViews(
//            1,
//            binding.tableLayout.childCount - 1
//        ) // Menghapus baris data lama, kecuali header
//
//        for ((index, product) in products.withIndex()) {
//            val tableRow = TableRow(context).apply {
//                layoutParams = TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT
//                )
//            }
//
//            // No (Nomor) Column
//            val noTextView = TextView(context).apply {
//                text = (index + 1).toString()
//                gravity = Gravity.CENTER
//                setPadding(12, 12, 12, 12)
//            }
//
//            // Info Produk Column
//            val productInfoLayout = LinearLayout(context).apply {
//                orientation = LinearLayout.HORIZONTAL
//                gravity = Gravity.CENTER
//                setPadding(12, 12, 12, 12)
//            }
//
//            val productImage = ImageView(context).apply {
//                setImageResource(R.drawable.produk) // Sesuaikan dengan gambar produk
//            }
//
//            val productName = TextView(context).apply {
//                text = product.name
//                textSize = 12f
//                setPadding(8, 0, 0, 0)
//            }
//
//            productInfoLayout.addView(productImage)
//            productInfoLayout.addView(productName)
//
//            // Jenis Column
//            val typeTextView = TextView(context).apply {
//                text = product.type
//                gravity = Gravity.CENTER
//                setPadding(12, 12, 12, 12)
//            }
//
//            // Kategori Column
//            val categoryTextView = TextView(context).apply {
//                text = product.category
//                gravity = Gravity.CENTER
//                setPadding(12, 12, 12, 12)
//            }
//
//            // Harga Column
//            val priceLayout = LinearLayout(context).apply {
//                orientation = LinearLayout.HORIZONTAL
//                gravity = Gravity.CENTER
//            }
//
//            val priceTextView = TextView(context).apply {
//                text = "Rp"
//                setPadding(4, 0, 0, 0)
//            }
//
//            val priceValueTextView = TextView(context).apply {
//                text = product.price.toString()
//                setPadding(4, 0, 0, 0)
//            }
//
//            priceLayout.addView(priceTextView)
//            priceLayout.addView(priceValueTextView)
//
//            // Stok Column
//            val stockTextView = TextView(context).apply {
//                text = product.stock.toString()
//                gravity = Gravity.CENTER
//                setPadding(12, 12, 12, 12)
//            }
//
//            // Switch Column (Active)
//            val switchActive = context?.let {
//                SwitchMaterial(it).apply {
//                    isChecked = product.isActive
//                    setPadding(12, 12, 12, 12)
//                }
//            }
//
//            // Action Column
//            val actionTextView = TextView(context).apply {
//                text = "Action"
//                gravity = Gravity.CENTER
//                setPadding(12, 12, 12, 12)
//            }
//
//            tableRow.addView(noTextView)
//            tableRow.addView(productInfoLayout)
//            tableRow.addView(typeTextView)
//            tableRow.addView(categoryTextView)
//            tableRow.addView(priceLayout)
//            tableRow.addView(stockTextView)
//            tableRow.addView(switchActive)
//            tableRow.addView(actionTextView)
//
//            binding.tableLayout.addView(tableRow)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
