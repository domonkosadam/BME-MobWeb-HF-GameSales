package hu.bme.aut.android.gamesales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.gamesales.R
import hu.bme.aut.android.gamesales.databinding.GameListRowBinding
import hu.bme.aut.android.gamesales.model.response.DealInfo

class GameListAdapter(private val context: Context, private val listener: OnDealSelectedListener) : RecyclerView.Adapter<GameListAdapter.GameListViewHolder>() {
    private var deals: MutableList<DealInfo> = mutableListOf()
    private val originalDeals: MutableList<DealInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_list_row, parent, false)
        return GameListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val item = deals[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = deals.size

    fun addDeals(newJobs: List<DealInfo>) {
        deals.addAll(newJobs)
        originalDeals.addAll(newJobs)
        notifyDataSetChanged()
    }

    fun filterItems(priceFilter: Array<Int>, percentFilter: Array<Int>) {
        deals = originalDeals.filter { e -> e.salePrice!!.toFloat() >= priceFilter[0] &&
                e.salePrice.toFloat() <= priceFilter[1] &&
                ((1f - (e.salePrice!!.toFloat()  / e.normalPrice!!.toFloat())) * 100) >= percentFilter[0] &&
                ((1f - (e.salePrice!!.toFloat()  / e.normalPrice!!.toFloat())) * 100) <= percentFilter[1]
        } as MutableList<DealInfo>
        notifyDataSetChanged()
    }

    interface OnDealSelectedListener {
        fun onDealSelected(deal: DealInfo?)
    }

    inner class GameListViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: DealInfo? = null
        var binding = GameListRowBinding.bind(itemView)

        init {
            binding.root.setOnClickListener { listener.onDealSelected(item) }
        }

        fun bind(newDeal: DealInfo?) {
            item = newDeal
            Glide.with(context).load(item?.thumb).into(binding.ivGameImage);
            binding.tvTitle.text = item?.title
            binding.tvNormalPrice.text = item?.normalPrice
            binding.tvSalePrice.text = item?.salePrice
            val normalPrice:Float = item?.normalPrice?.toFloat() ?: 0f
            val salePrice = item?.salePrice?.toFloat() ?: 0f
            binding.tvSalePercent.text = "-${((1f - (salePrice / normalPrice)) * 100).toInt()}%"
        }
    }
}