import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItemAdmin
import com.example.ptamanah.data.retrofit.ApiService
import com.example.ptamanah.databinding.ListItemEventAdminBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventAdminLogAdapter(
    private val username: String,
    private val apiService: ApiService,
    private val token: String
) : PagingDataAdapter<DataItemAdmin, EventAdminLogAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemEventAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(private val binding: ListItemEventAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataItemAdmin) {
            binding.nomor.text = data.invoice
            binding.namaPemilikIsi.text = data.nama
            binding.namaTiketIsi.text = data.namaTiket
            binding.statusIsi.text = data.status


            binding.KodeBookingIsi.text = data.bookingCode
            binding.namaPemilikIsi2.text = data.nama
            binding.emailIsi.text = data.email
            binding.noHpIsi.text = data.handphone
            binding.waktuCheckIsi.text = data.checkinTime.toString()
            binding.jenisTiketIsi.text = data.namaTiket
            binding.kuotaIsi.text = data.kuota.toString()
            binding.statusIsi2.text = data.status

            binding.previewBtn.setOnClickListener {
                val context = itemView.context
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://berbagi.link/$username/event/tickets/${data.slug}/${data.bookingCode}")
                )
                context.startActivity(intent)
            }
            var isExpanded = false
            binding.btnExpand.setOnClickListener {
                isExpanded = !isExpanded
                if (isExpanded) {
                    binding.expandLayout.visibility = View.VISIBLE
                    binding.expandLayout3Teks.visibility = View.GONE
                    binding.btnExpand.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_arrow_down)
                } else {
                    binding.expandLayout.visibility = View.GONE
                    binding.expandLayout3Teks.visibility = View.VISIBLE
                    binding.btnExpand.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_arrow_up)
                }
            }

            binding.btnCheck.setOnClickListener {
                // Use a coroutine to make the network call
                (itemView.context as? FragmentActivity)?.lifecycleScope?.launch {
                    try {
                        // Log data.id
                        Log.d("todssss", "Data id: ${data.id}")
                        Log.d("todssss", "Token: $token")
                        val tokenRill = "Bearer $token"

                        val response = withContext(Dispatchers.IO) {
                            apiService.updateCheckin( tokenRill, data.id ?: "")
                        }
                        if (response.error == false) {
                            Toast.makeText(itemView.context, "Check-in updated successfully", Toast.LENGTH_SHORT).show()
                            // Update the item with new data
                        } else {
                            Log.e("jjj", "Error updating check-in")
                            Toast.makeText(itemView.context, response.message ?: "Failed to update check-in", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("aaa", "Error updating check-in", e)
                        Toast.makeText(itemView.context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemAdmin>() {
            override fun areItemsTheSame(oldItem: DataItemAdmin, newItem: DataItemAdmin): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemAdmin,
                newItem: DataItemAdmin
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
