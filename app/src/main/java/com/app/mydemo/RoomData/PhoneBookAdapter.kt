package com.app.mydemo.RoomData

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mydemo.R
import com.app.mydemo.databinding.ItemPhoneBookBinding

class PhoneBookAdapter(
    private val context: Context,
    private val pastHistoryList: List<PhoneBook>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<PhoneBookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPhoneBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding)
        {
            with(pastHistoryList[holder.adapterPosition])
            {
                tvName.text = String.format(context.resources.getString(R.string.name_colon, this.name))
                tvPhone.text = String.format(context.resources.getString(R.string.phone_colon, this.number))
                tvAddress.text = String.format(context.resources.getString(R.string.address_colon, this.address))
            }
        }

        holder.binding.root.setOnClickListener {
            onClickListener.onItemClickPhoneBook(pastHistoryList.get(holder.adapterPosition))
        }

        holder.binding.ivDelete.setOnClickListener {
            onClickListener.onItemRemovePhoneBook(pastHistoryList.get(holder.adapterPosition))
        }
    }

    override fun getItemCount(): Int {
        return pastHistoryList.size
    }

    class ViewHolder(val binding: ItemPhoneBookBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}