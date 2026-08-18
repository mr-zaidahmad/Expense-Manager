package com.example.expensemanager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.util.Locale


class TransactionAdapter(
    private var transactions: List<RoomdatabaseTransaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    class TransactionViewHolder(
        val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {

        val binding = ItemTransactionBinding.inflate(
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
        holder.binding.TransactionCategory.text =
            transaction.category


        // Wallet
        holder.binding.TransactionWallet.text =
            transaction.wallet


        // Date / Time
        holder.binding.TransactionDate.text =
            transaction.date


        // Set category icon
        setCategoryIcon(
            holder,
            transaction.category
        )


        // Amount
        val amount = transaction.amount


        if (transaction.type == "EXPENSE") {

            holder.binding.TransactionAmount.text =
                "-Rs. ${formatNumber(amount)}"

            holder.binding.TransactionAmount.setTextColor(
                Color.rgb(231, 76, 76)
            )

        } else {

            holder.binding.TransactionAmount.text =
                "+Rs. ${formatNumber(amount)}"

            holder.binding.TransactionAmount.setTextColor(
                Color.rgb(66, 165, 245)
            )
        }
    }


    private fun setCategoryIcon(
        holder: TransactionViewHolder,
        category: String
    ) {

        when (category) {

            // =========================
            // EXPENSE CATEGORIES
            // =========================

            "Bills" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.billss
                )
            }

            "Cloth" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.clothh
                )
            }

            "Education" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.education
                )
            }

            "Entertainment" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.entertainment
                )
            }

            "Fitness" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.fitness
                )
            }

            "Food" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.foodd
                )
            }

            "Gifts" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.gift
                )
            }

            "Health" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.health
                )
            }

            "Furniture" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.furnituree
                )
            }

            "Pet" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.pet
                )
            }


            // =========================
            // INCOME CATEGORIES
            // =========================

            "Allowance" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.allowance
                )
            }

            "Award" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.award
                )
            }

            "Bonus" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.bonus
                )
            }

            "Dividend" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.dividend
                )
            }

            "Investment" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.investement
                )
            }

            "Lottery" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.lottery
                )
            }

            "Salary" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.salary
                )
            }

            "Tips" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.tips
                )
            }

            "Business" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.business
                )
            }

            "Others" -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.otherssvg
                )
            }


            // =========================
            // UNKNOWN CATEGORY
            // =========================

            else -> {
                holder.binding.TransactionIcon.setBackgroundResource(
                    R.drawable.otherssvg
                )
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