package com.example.ptamanah.view.settings

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.ptamanah.R
import com.example.ptamanah.adapter.navigation.ExpandableListAdapter
import com.example.ptamanah.adapter.navigation.NavMenuItem
import com.example.ptamanah.databinding.ActivityPageBinding
import com.example.ptamanah.view.marketing.AnalisisFragment
import com.example.ptamanah.view.marketing.AnggotaFragment
import com.example.ptamanah.view.marketing.TransaksiFragment
import com.example.ptamanah.view.produk.DaftarProdukFragment
import com.example.ptamanah.view.penjualan.logTransaksi.PenjualanLogTransaksi
import com.example.ptamanah.view.penjualan.transaksi.PenjualanTransaksi
import com.example.ptamanah.view.penjualan.voucher.PenjualanVoucher
import com.example.ptamanah.view.settings.banner.DaftarBanner
import com.example.ptamanah.view.settings.halaman.HalamanFragment
import com.example.ptamanah.view.settings.rekening.RekeningFragment

class PageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPageBinding

    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: ExpandableListAdapter
    private lateinit var titleList: List<String>
    private lateinit var childList: HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        expandableListView = binding.expandableListView

        setupExpandableList()

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)

        headerView.findViewById<ImageButton>(R.id.close).setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        if (savedInstanceState == null) {
            replaceFragment(HalamanFragment())
        }

    }


    private fun setupExpandableList() {
        val expandableListView: ExpandableListView = findViewById(R.id.expandableListView)

        val menuItems = listOf(
            NavMenuItem(
                id = "menu",
                title = "Menu",
            ),
            NavMenuItem(id = "beranda", title = "Beranda", icon = R.drawable.ic_home),
            NavMenuItem(id = "ubah_tampilan", title = "Ubah Tampilan", icon = R.drawable.ic_home),
            NavMenuItem(id = "statistik", title = "Statistik", icon = R.drawable.ic_home),
            NavMenuItem(id = "bagi_to", title = "Bagi.to", icon = R.drawable.ic_home),
            NavMenuItem(id = "tagihan", title = "Tagihan", icon = R.drawable.ic_home),
            NavMenuItem(
                id = "tracking_pixels",
                title = "Tracking Pixels",
                icon = R.drawable.ic_home
            ),
            NavMenuItem(
                id = "toko_online", title = "Toko Online", icon = R.drawable.ic_home,
                children = listOf(
                    NavMenuItem(
                        id = "pengaturan",
                        title = "Pengaturan",
                        icon = R.drawable.ic_home,
                        children = listOf(
                            NavMenuItem(
                                id = "halaman", title = "Halaman", icon = R.drawable.ic_home
                            ),
                            NavMenuItem(
                                id = "banner",
                                title = "Banner",
                                icon = R.drawable.ic_home
                            ),
                            NavMenuItem(
                                id = "rekening",
                                title = "Rekening",
                                icon = R.drawable.ic_home,
                            ),
                        )
                    ),
                    NavMenuItem(id = "produk", title = "Produk", icon = R.drawable.ic_home),
                    NavMenuItem(id = "penjualan", title = "Penjualan", icon = R.drawable.ic_home,
                        children = listOf(
                            NavMenuItem(
                                id = "transaksi",
                                title = "Transaksi",
                                icon = R.drawable.ic_home,
                            ),
                            NavMenuItem(
                                id = "voucher",
                                title = "Voucher",
                                icon = R.drawable.ic_home,
                            ),
                            NavMenuItem(
                                id = "log_transaksi",
                                title = "Log Transaksi",
                                icon = R.drawable.ic_home,
                            )
                        )),
                    NavMenuItem(id = "marketing", title = "Marketing", icon = R.drawable.ic_home),
                    NavMenuItem(id = "penjualan", title = "Penjualan", icon = R.drawable.ic_home),
                    NavMenuItem(
                        id = "marketing",
                        title = "Marketing",
                        icon = R.drawable.ic_home, children = listOf(
                            NavMenuItem(
                                id = "anggota",
                                title = "Anggota",
                                icon = R.drawable.ic_home,
                            ),
                            NavMenuItem(
                                id = "transaksi",
                                title = "Transaksi",
                                icon = R.drawable.ic_home,
                            ), NavMenuItem(
                                id = "analisis",
                                title = "Analisis",
                                icon = R.drawable.ic_home,
                            ),
                        )
                    )
                )
            ),
            NavMenuItem(id = "event", title = "Event", icon = R.drawable.ic_home),
            NavMenuItem(id = "payment", title = "Payment", icon = R.drawable.ic_home),
            NavMenuItem(id = "wallet", title = "Wallet", icon = R.drawable.ic_home),
            NavMenuItem(
                id = "support",
                title = "Support",
                badge = "BUSINESS",
                icon = R.drawable.ic_home
            ),
            NavMenuItem(
                id = "donasi",
                title = "Donasi",
                badge = "BUSINESS",
                icon = R.drawable.ic_home
            ),
            NavMenuItem(
                id = "landing_page",
                title = "Landing Page",
                badge = "BUSINESS",
                icon = R.drawable.ic_home
            )
        )

        val adapter = ExpandableListAdapter(this, menuItems) { menuItem ->
            onMenuItemClick(menuItem)
        }
        expandableListView.setAdapter(adapter)

        // Handle click events
        expandableListView.setOnGroupClickListener { _, _, groupPosition, _ ->
            adapter.toggleItem(groupPosition)
            true
        }

        // Optional: Hapus divider
        expandableListView.setGroupIndicator(null)
        expandableListView.divider =
            ColorDrawable(ContextCompat.getColor(this, R.color.divider_color))
        expandableListView.dividerHeight = 1.dp(this)
    }

    private fun onMenuItemClick(menuItem: NavMenuItem) {
        when (menuItem.id) {
            "halaman" -> {
                replaceFragment(HalamanFragment())
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }, 150)
            }

            "banner" -> {
                replaceFragment(DaftarBanner())
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }, 150)
            }

            "rekening" -> {
                replaceFragment(RekeningFragment())
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }, 150)
            }

            //Penjualan(sales)
            "transaksi" -> {
                replaceFragment(PenjualanTransaksi())
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }, 150)
            }

            "voucher" -> {
                replaceFragment(PenjualanVoucher())
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }, 150)
            }

            "log_transaksi" -> {
                replaceFragment(PenjualanLogTransaksi())
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }, 150)
            }

            "produk" -> {
                replaceFragment(DaftarProdukFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "anggota" -> {
                replaceFragment(AnggotaFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "transaksi" -> {
                replaceFragment(TransaksiFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "analisis" -> {
                replaceFragment(AnalisisFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            else -> {
                Toast.makeText(this, "Item ${menuItem.title} clicked", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.containerToko)
        if (currentFragment?.javaClass != fragment.javaClass) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setReorderingAllowed(true)
            transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            transaction.replace(R.id.containerToko, fragment)
            transaction.addToBackStack(null)
            transaction.commitAllowingStateLoss()
        }
    }

    fun Int.dp(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    androidx.cardview.R.color.cardview_light_background
                )
            )
        )
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_icon -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}