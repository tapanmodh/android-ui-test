package com.speechify.android.ui.test.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.speechify.android.ui.test.R
import com.speechify.android.ui.test.data.Book
import com.speechify.android.ui.test.databinding.ActivityHomeBinding
import com.speechify.android.ui.test.fragments.BookDetailsFragment

interface OnClickListener {
    fun onClick(book: Book)
}

class HomeActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityHomeBinding.inflate(layoutInflater).root
        )
    }

    override fun onClick(book: Book) {
        supportFragmentManager.commit {
            addToBackStack(null)
            add(R.id.nav_host_fragment, BookDetailsFragment.newInstance(book))
        }
    }
}