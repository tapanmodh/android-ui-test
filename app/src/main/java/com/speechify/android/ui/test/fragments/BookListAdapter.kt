package com.speechify.android.ui.test.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.speechify.android.ui.test.activity.OnClickListener
import com.speechify.android.ui.test.data.Book
import com.speechify.android.ui.test.databinding.ItemBookBinding
import com.speechify.android.ui.test.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListAdapter(private var onClickListener: OnClickListener) : RecyclerView.Adapter<BookListAdapter.VHBook>() {

    private var bookList: List<Book>?=null
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.VHBook {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding =
            ItemBookBinding.inflate(layoutInflater, parent, false)
        return VHBook(viewBinding)
    }

    inner class VHBook(private val viewBinding: ItemBookBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {

            val book = bookList?.get(position)?:return
            val lifecycleOwner by lazy{
                viewBinding.root.context as? LifecycleOwner
            }
            lifecycleOwner?.lifecycleScope?.launch {
                val bookImage = BookRepository().getBookImageBitmap(book.id)
                withContext(Dispatchers.Main) {
                    viewBinding.imageViewBookCover.setImageBitmap(bookImage)
                }
            }
            viewBinding.textViewBookTitle.text = book.title
            viewBinding.textViewBookAuthor.text = book.author

            viewBinding.root.setOnClickListener {
                onClickListener.onClick(book)
            }
        }
    }

    override fun getItemCount(): Int {
        return bookList?.size?:0
    }

    override fun onBindViewHolder(holder: BookListAdapter.VHBook, position: Int) {
        holder.bind(position)
    }
    
    fun setBookList(bookList: List<Book>?) {
        this.bookList = bookList
        notifyDataSetChanged()
    }
}