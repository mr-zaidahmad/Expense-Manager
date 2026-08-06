package com.example.expensemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.databinding.ItemAccountBinding

class AccountAdapter(
    private var accountList: List<RoomdatabaseUserdata>,
    private val onClick: (RoomdatabaseUserdata) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    class AccountViewHolder(val binding: ItemAccountBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountViewHolder {

        val binding = ItemAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AccountViewHolder,
        position: Int
    ) {

        val account = accountList[position]

        holder.binding.tvAccountName.text = account.name
        holder.binding.tvCurrency.text = account.Currency

        holder.itemView.setOnClickListener {
            onClick(account)
        }
    }

    override fun getItemCount() = accountList.size

    fun updateList(newList: List<RoomdatabaseUserdata>) {
        accountList = newList
        notifyDataSetChanged()
    }
}