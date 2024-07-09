package com.example.esgithub.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.esgithub.databinding.ActivityProgramDetailsBinding
import com.example.esgithub.di.injectModuleDependencies
import com.example.esgithub.di.parseAndInjectConfiguration
import com.example.esgithub.models.program.request.CommentRequest
import com.example.esgithub.ui.view.adapters.CommentListAdapter
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_CREATED_AT
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_DESCRIPTION
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_ID
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_LIKES_SIZE
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_SOURCE_CODE
import com.example.esgithub.ui.viewModels.CommentViewModel
import com.example.esgithub.utils.TokenManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgramDetailsActivity : AppCompatActivity() {
    private val commentViewModel: CommentViewModel by viewModel()

    private lateinit var binding: ActivityProgramDetailsBinding

    private lateinit var programId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgramDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        observeProductComments()
        displayProgramDataFromIntent()
        publishComment()
    }

    private fun displayProgramDataFromIntent() {
        binding.programDetailCreatedAtTv.text = intent.getStringExtra(PROGRAM_CREATED_AT)
        binding.programDetailsDescription.text = intent.getStringExtra(PROGRAM_DESCRIPTION)
        binding.likesSizeIv.text = intent.getStringExtra(PROGRAM_LIKES_SIZE)
        binding.programCodeSource.text = intent.getStringExtra(PROGRAM_SOURCE_CODE)

        this.programId = intent.getStringExtra(PROGRAM_ID).toString()

        programId.let {
            this.commentViewModel.loadProgramComments(it)
        }
    }

    private fun observeProductComments() {
        this.commentViewModel.programComments.observe(this) { comments ->
            binding.commentsRv.layoutManager = LinearLayoutManager(this)
            binding.commentsRv.adapter = CommentListAdapter(comments)
            binding.commentsSizeTv.text = comments.count { it.codeLineNumber == null }.toString()
        }
    }

    private fun publishComment() {
        binding.publishCommentBtn.setOnClickListener {
            this.addProgramComment()
            binding.addCommentInput.text.clear()
        }
    }

    private fun addProgramComment() {
        val comment = binding.addCommentInput.text.toString()

        if (comment.isNotEmpty()) {
            val commentRequest = CommentRequest(comment, TokenManager.getUserIdFromToken(), this.programId)
            this.commentViewModel.addComment(commentRequest)
        }
    }
}
