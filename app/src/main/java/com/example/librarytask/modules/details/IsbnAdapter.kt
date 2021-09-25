package com.example.librarytask.modules.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.librarytask.R

class IsbnAdapter : ListAdapter<String, IsbnAdapter.IsbnViewHolder>(IsbnDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IsbnViewHolder =
        IsbnViewHolder.from(parent)


    override fun onBindViewHolder(holder: IsbnViewHolder, position: Int) =
        holder.bind(getItem(position))


    class IsbnViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        companion object {
            fun from(parent: ViewGroup): IsbnViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val root = inflater.inflate(R.layout.list_item_isbn, parent, false)
                return IsbnViewHolder(root)
            }
        }

        fun bind(currentIsbn: String) {
            root.findViewById<TextView>(R.id.docs_isbn_tv).text = currentIsbn
        }
    }

    class IsbnDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

}