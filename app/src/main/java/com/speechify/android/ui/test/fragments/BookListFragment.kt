package com.speechify.android.ui.test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.speechify.android.ui.test.activity.HomeActivity
import com.speechify.android.ui.test.data.Book
import com.speechify.android.ui.test.databinding.FragmentBookListBinding
import com.speechify.android.ui.test.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListFragment : Fragment() {

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!
    private var mBookList: List<Book>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.rvBookList?.setHasFixedSize(true)
        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        _binding?.rvBookList?.layoutManager = llm
        val adapter = BookListAdapter(activity as HomeActivity)
        _binding?.rvBookList?.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            mBookList = BookRepository().getBooks()
         withContext(Dispatchers.Main) {

                    adapter.setBookList(mBookList)
                }
            }
        }
}