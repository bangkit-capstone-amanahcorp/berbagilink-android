import android.content.Intent
import android.net.Uri
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
import com.example.ptamanah.view.admin.logcheck.OnCheckInSuccessListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventAdminLogAdapter(
    private val username: String,
    private val apiService: ApiService,
    private val token: String,
    private val listener: OnCheckInSuccessListener
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
            binding.tvStatusAwal.text = data.status
            binding.KodeBookingIsi.text = data.bookingCode
            binding.namaPemilikIsi2.text = data.nama
            binding.emailIsi.text = data.email
            binding.noHpIsi.text = data.handphone
            binding.waktuCheckIsi.text = data.checkinTime.toString()
            binding.jenisTiketIsi.text = data.namaTiket
            binding.kuotaIsi.text = data.kuota.toString()
            binding.tvStatus.text = data.status

            if (data.status == "check-in") {
                binding.statusIVAwal.setImageResource(R.drawable.status_checkin)
                binding.tvStatusAwal.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_checkin))
                binding.statusIV.setImageResource(R.drawable.status_checkin)
                binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_checkin))
            }
            else if (data.status == "uncheck") {
                binding.statusIVAwal.setImageResource(R.drawable.status_uncheckin)
                binding.statusIV.setImageResource(R.drawable.status_uncheckin)
                binding.tvStatusAwal.setTextColor(ContextCompat.getColor(itemView.context, R.color.oren))
                binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.oren))
            } else if (data.status == "returned") {
                binding.statusIVAwal.setImageResource(R.drawable.status_returned)
                binding.statusIV.setImageResource(R.drawable.status_returned)
                binding.tvStatusAwal.setTextColor(ContextCompat.getColor(itemView.context, R.color.returned))
                binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.returned))
            } else{
                binding.statusIVAwal.setImageResource(R.drawable.status_failed)
                binding.statusIV.setImageResource(R.drawable.status_failed)
                binding.tvStatusAwal.setTextColor(ContextCompat.getColor(itemView.context, R.color.failed))
                binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.failed))
            }

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
                    binding.card.setStrokeColor(ContextCompat.getColor(itemView.context, R.color.biru_toska))

                } else {
                    binding.expandLayout.visibility = View.GONE
                    binding.expandLayout3Teks.visibility = View.VISIBLE
                    binding.btnExpand.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_arrow_up)
                    binding.card.setStrokeColor(ContextCompat.getColor(itemView.context, R.color.disable))
                }
            }

            if (data.status == "check-in") {
                binding.btnCheck.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.biru_toska)
                binding.btnCheck.text = "Check in"
            } else {
                binding.btnCheck.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.failed)
            }
            binding.btnCheck.setOnClickListener {
                (itemView.context as? FragmentActivity)?.lifecycleScope?.launch {
                    try {
                        binding.progressBar.visibility = View.VISIBLE
                        val tokenRill = "Bearer $token"
                        val response = withContext(Dispatchers.IO) {
                            apiService.updateCheckin( tokenRill, data.id)
                        }
                        if (response.error == false) {
                            listener.onCheckInSuccess()
                            Toast.makeText(itemView.context, "Check-in updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(itemView.context, response.message ?: "Failed to update check-in", Toast.LENGTH_SHORT).show()
                        }
                        binding.progressBar.visibility = View.GONE
                    } catch (e: Exception) {
                        Toast.makeText(itemView.context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
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
