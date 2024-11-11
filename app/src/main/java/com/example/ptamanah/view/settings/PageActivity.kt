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
import com.example.ptamanah.view.settings.halaman.MediaSosialTokoFragment
import com.example.ptamanah.R
import com.example.ptamanah.view.settings.halaman.TokoFragment
import com.example.ptamanah.view.settings.halaman.TransaksiFragment
import com.example.ptamanah.adapter.navigation.ExpandableListAdapter
import com.example.ptamanah.adapter.navigation.NavMenuItem
import com.example.ptamanah.databinding.ActivityPageBinding
import com.example.ptamanah.view.settings.banner.DaftarBanner
import com.example.ptamanah.view.settings.banner.EditBanner
import com.example.ptamanah.view.settings.banner.NyobaFragment
import com.example.ptamanah.view.settings.banner.TambahBanner
import com.example.ptamanah.view.settings.halaman.HalamanFragment
import com.example.ptamanah.view.settings.halaman.PreviewFragment
import com.example.ptamanah.view.settings.rekening.EditRekeningFragment
import com.example.ptamanah.view.settings.rekening.RekeningFragment
import com.example.ptamanah.view.settings.rekening.TambahRekeningFragment

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
                                id = "halaman", title = "Halaman", icon = R.drawable.ic_home,
                                children = listOf(
                                    NavMenuItem(
                                        id = "toko_fragment",
                                        title = "Toko Fragment",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "media_sosial_toko_fragment",
                                        title = "Media Sosial Toko Fragment",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "transaksi_fragment",
                                        title = "Transaksi Fragment",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "preview_fragment",
                                        title = "Preview Fragment",
                                        icon = R.drawable.ic_home,
                                    ),
                                )
                            ),
                            NavMenuItem(
                                id = "banner",
                                title = "Banner",
                                icon = R.drawable.ic_home,
                                children = listOf(
                                    NavMenuItem(
                                        id = "daftar_banner",
                                        title = "Daftar Banner",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "edit_banner",
                                        title = "Edit Banner",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "tambah_banner",
                                        title = "Tambah Banner",
                                        icon = R.drawable.ic_home,
                                    )
                                )),
                            NavMenuItem(
                                id = "rekening",
                                title = "Rekening",
                                icon = R.drawable.ic_home,
                                children = listOf(
                                    NavMenuItem(
                                        id = "rekening_fragment",
                                        title = "Rekening",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "tambah_rekening_fragment",
                                        title = "Tambah Rekening",
                                        icon = R.drawable.ic_home,
                                    ),
                                    NavMenuItem(
                                        id = "edit_rekening_fragment",
                                        title = "Edit Rekening",
                                        icon = R.drawable.ic_home,
                                    )
                                )
                            )
                        )
                    ),
                    NavMenuItem(id = "produk", title = "Produk", icon = R.drawable.ic_home),
                    NavMenuItem(id = "penjualan", title = "Penjualan", icon = R.drawable.ic_home),
                    NavMenuItem(id = "marketing", title = "Marketing", icon = R.drawable.ic_home),
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
//        val fragment: Fragment = when (menuItem.id) {
//            "beranda" -> throw IllegalArgumentException("Unknown menu item")
//            "ubah_tampilan" -> throw IllegalArgumentException("Unknown menu item")
//            "statistik" -> throw IllegalArgumentException("Unknown menu item")
//            "halaman" -> HalamanFragment()
//            "event" -> throw IllegalArgumentException("Unknown menu item")
//            else -> throw IllegalArgumentException("Unknown menu item")
//        }

        // Ganti fragment di FragmentContainerView
//        replaceFragment(fragment)
        when (menuItem.id) {
            "beranda" -> {
                replaceFragment(TokoFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "toko_fragment" -> {
                replaceFragment(TokoFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "media_sosial_toko_fragment" -> {
                replaceFragment(MediaSosialTokoFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "statistik" -> {
                replaceFragment(TransaksiFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            // Add other menu items as needed
            "transaksi_fragment" -> {
                replaceFragment(TransaksiFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "preview_fragment" -> {
                replaceFragment(PreviewFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "daftar_banner" -> {
                replaceFragment(DaftarBanner())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "edit_banner" -> {
                replaceFragment(EditBanner())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "tambah_banner" -> {
                replaceFragment(TambahBanner())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "rekening_fragment" -> {
                replaceFragment(RekeningFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "tambah_rekening_fragment" -> {
                replaceFragment(TambahRekeningFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            "edit_rekening_fragment" -> {
                replaceFragment(EditRekeningFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            else -> {
                Toast.makeText(this, "Item ${menuItem.title} clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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