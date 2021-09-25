package com.example.librarytask.modules.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.librarytask.R
import com.example.librarytask.data.document.Docs

class DocumentsAdapter(
    private val docClickListener: DocClickListener,
    private val loadMore: () -> Unit
) :
    ListAdapter<DocumentsAdapter.DataItemHolder, RecyclerView.ViewHolder>(DocumentDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DOCS_VIEW_TYPE -> DocumentsViewHolder.from(parent)
            FOOTER_VEW_TYPE -> FooterViewHolder.from(parent)
            else -> throw ClassCastException("unknown viewholder")
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == currentList.size - 1 && getItem(position) is DataItemHolder.DocsHolder) {
            loadMore()
        }
        if (getItem(position) is DataItemHolder.DocsHolder) {
            val item = getItem(position) as DataItemHolder.DocsHolder
            (holder as DocumentsViewHolder).bind(item.docs, docClickListener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItemHolder.DocsHolder -> DOCS_VIEW_TYPE
            is DataItemHolder.FooterHolder -> FOOTER_VEW_TYPE
        }
    }

    class DocumentsViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        companion object {
            fun from(parent: ViewGroup): DocumentsViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val root = inflater.inflate(R.layout.list_item_document, parent, false)
                return DocumentsViewHolder(root)
            }
        }

        fun bind(currentDoc: Docs, docClickListener: DocClickListener) {
            root.findViewById<TextView>(R.id.docs_title_tv).text = currentDoc.title
            root.findViewById<TextView>(R.id.docs_author_tv).text =
                currentDoc.author_name?.get(0)
            root.setOnClickListener {
                docClickListener.onCLick(currentDoc)
            }

        }
    }

    class FooterViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        companion object {
            fun from(parent: ViewGroup): FooterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val root = inflater.inflate(R.layout.loading, parent, false)
                return FooterViewHolder(root)
            }
        }
    }


    class DocumentDiffCallback : DiffUtil.ItemCallback<DataItemHolder>() {
        override fun areItemsTheSame(oldItem: DataItemHolder, newItem: DataItemHolder) =
            oldItem.key == newItem.key

        override fun areContentsTheSame(oldItem: DataItemHolder, newItem: DataItemHolder) =
            oldItem == newItem
    }

    class DocClickListener(val clickListener: (Docs) -> Unit) {
        fun onCLick(docs: Docs) = clickListener(docs)
    }

    sealed class DataItemHolder {
        abstract val key: String?

        class DocsHolder(val docs: Docs) : DataItemHolder() {
            override val key: String?
                get() = docs.key
        }

        class FooterHolder : DataItemHolder() {
            override val key: String?
                get() = ""
        }

    }


    companion object {
        const val FOOTER_VEW_TYPE = 0
        const val DOCS_VIEW_TYPE = 1
    }
}