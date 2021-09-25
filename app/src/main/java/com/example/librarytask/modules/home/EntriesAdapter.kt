package com.example.librarytask.modules.home

import Entry
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.librarytask.R

class EntriesAdapter :
    ListAdapter<Entry, EntriesAdapter.EntryViewHolder>(EntryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder =
        EntryViewHolder.from(parent)


    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) =
        holder.bind(getItem(position))


    class EntryViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        companion object {
            fun from(parent: ViewGroup): EntryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val root = inflater.inflate(R.layout.list_item_entry, parent, false)
                return EntryViewHolder(root)
            }
        }

        fun bind(currentEntry: Entry) {
            root.findViewById<TextView>(R.id.entry_title_tv).text = currentEntry.title
        }

    }


    class EntryDiffCallback : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry) =
            oldItem.key == newItem.key

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry) =
            oldItem == newItem
    }

}