package com.example.expensemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.databinding.ItemAccountBinding

class AccountAdapter(
    private var accountList: List<RoomdatabaseUserdata>,
    private val onClick: (RoomdatabaseUserdata) -> Unit,
    private val onDeleteClick: (RoomdatabaseUserdata) -> Unit = {}
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    class AccountViewHolder(
        val binding: ItemAccountBinding
    ) : RecyclerView.ViewHolder(binding.root)

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

        holder.binding.tvCurrency.text =
            "${account.Currency} ${String.format("%,.0f", account.InitialAmount.toDouble())}"

        // Click account to edit
        holder.itemView.setOnClickListener {
            onClick(account)
        }

        // Click delete
        holder.binding.DeleteAccountIcon.setOnClickListener {
            onDeleteClick(account)
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    fun updateList(newList: List<RoomdatabaseUserdata>) {
        accountList = newList
        notifyDataSetChanged()
    }
}