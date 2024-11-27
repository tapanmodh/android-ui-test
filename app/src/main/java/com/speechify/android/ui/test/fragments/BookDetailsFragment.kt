package com.speechify.android.ui.test.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.speechify.android.ui.test.data.Book
import com.speechify.android.ui.test.databinding.FragmentBookDetailsBinding
import com.speechify.android.ui.test.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailsBinding? = null
    private val binding get() = _binding!!

    private val BOOK = "book"
    private var mBook: Book? = null

    companion object {
        fun newInstance(book: Book) = BookDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BOOK, book)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mBook =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(BOOK, Book::class.java)
                } else {
                    it.getParcelable(BOOK) as? Book
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBook?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                val bookImage = BookRepository().getBookImageBitmap(it.id)
                withContext(Dispatchers.Main) {
                    _binding?.ivBookCover?.setImageBitmap(bookImage)
                }
            }
            _binding?.txtTitle?.text = it.title
            _binding?.txtAuthor?.text = it.author
        }

    }

}