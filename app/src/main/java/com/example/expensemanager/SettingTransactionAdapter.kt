package com.example.expensemanager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.databinding.ItemSettingTransactionBinding
import java.text.NumberFormat
import java.util.Locale

class SettingTransactionAdapter(
    private var transactions: List<RoomdatabaseTransaction>,
    private val onDeleteClick: (RoomdatabaseTransaction) -> Unit
) : RecyclerView.Adapter<SettingTransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(
        val binding: ItemSettingTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {

        val binding = ItemSettingTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TransactionViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: TransactionViewHolder,
        position: Int
    ) {

        val transaction = transactions[position]

        // Category
        holder.binding.SettingTransactionCategory.text =
            transaction.category


        // Wallet
        holder.binding.SettingTransactionWallet.text =
            transaction.wallet


        // Date / Time
        holder.binding.SettingTransactionDate.text =
            transaction.date


        // Category icon
        setCategoryIcon(
            holder,
            transaction.category
        )


        // Amount
        val amount = transaction.amount

        if (transaction.type == "EXPENSE") {

            holder.binding.SettingTransactionAmount.text =
                "-Rs. ${formatNumber(amount)}"

            holder.binding.SettingTransactionAmount.setTextColor(
                Color.rgb(231, 76, 76)
            )

        } else {

            holder.binding.SettingTransactionAmount.text =
                "+Rs. ${formatNumber(amount)}"

            holder.binding.SettingTransactionAmount.setTextColor(
                Color.rgb(66, 165, 245)
            )
        }


        // Delete button
        holder.binding.SettingTransactionDelete.setOnClickListener {

            onDeleteClick(transaction)
        }
    }


    private fun setCategoryIcon(
        holder: TransactionViewHolder,
        category: String
    ) {

        when (category) {

            // =========================
            // EXPENSE
            // =========================

            "Bills" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.billss)
            }

            "Cloth" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.clothh)
            }

            "Education" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.education)
            }

            "Entertainment" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.entertainment)
            }

            "Fitness" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.fitness)
            }

            "Food" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.foodd)
            }

            "Gifts" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.gift)
            }

            "Health" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.health)
            }

            "Furniture" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.furnituree)
            }

            "Pet" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.pet)
            }


            // =========================
            // INCOME
            // =========================

            "Allowance" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.allowance)
            }

            "Award" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.award)
            }

            "Bonus" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.bonus)
            }

            "Dividend" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.dividend)
            }

            "Investment" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.investement)
            }

            "Lottery" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.lottery)
            }

            "Salary" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.salary)
            }

            "Tips" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.tips)
            }

            "Business" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.business)
            }

            "Others" -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.otherssvg)
            }

            else -> {
                holder.binding.SettingTransactionIcon
                    .setBackgroundResource(R.drawable.otherssvg)
            }
        }
    }


    override fun getItemCount(): Int {
        return transactions.size
    }


    fun updateTransactions(
        newTransactions: List<RoomdatabaseTransaction>
    ) {

        transactions = newTransactions

        notifyDataSetChanged()
    }


    private fun formatNumber(
        amount: Double
    ): String {

        return NumberFormat
            .getNumberInstance(Locale.US)
            .apply {
                maximumFractionDigits = 2
            }
            .format(amount)
    }
}