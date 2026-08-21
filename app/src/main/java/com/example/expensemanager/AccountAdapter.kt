package com.example.expensemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.databinding.ItemAccountBinding

class AccountAdapter(
    private var accountList: List<RoomdatabaseUserdata>,
    private val onClick: (RoomdatabaseUserdata) -> Unit,
    private val onDeleteClick: (RoomdatabaseUserdata) -> Unit = {},
    private val showDeleteButton: Boolean = true
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    class AccountViewHolder(
        val binding: ItemAccountBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountViewHolder {

        val binding =
            ItemAccountBinding.inflate(
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

        val account =
            accountList[position]

        // Account name
        holder.binding.tvAccountName.text =
            account.name

        // Currency and initial amount
        holder.binding.tvCurrency.text =
            "${account.Currency} ${
                String.format(
                    "%,.0f",
                    account.InitialAmount.toDouble()
                )
            }"

        // Select account
        holder.itemView.setOnClickListener {

            onClick(account)
        }

        // Delete account
        if (showDeleteButton) {

            holder.binding.DeleteAccountIcon.visibility =
                View.VISIBLE

            holder.binding.DeleteAccountIcon.setOnClickListener {

                onDeleteClick(account)
            }

        } else {

            holder.binding.DeleteAccountIcon.visibility =
                View.GONE

            holder.binding.DeleteAccountIcon.setOnClickListener(
                null
            )
        }
    }

    override fun getItemCount(): Int {

        return accountList.size
    }

    fun updateList(
        newList: List<RoomdatabaseUserdata>
    ) {

        accountList =
            newList

        notifyDataSetChanged()
    }
}