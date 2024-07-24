package com.example.esgithub.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esgithub.databinding.FragmentHomeBinding
import com.example.esgithub.di.injectModuleDependencies
import com.example.esgithub.di.parseAndInjectConfiguration
import com.example.esgithub.models.program.ProgramModel
import com.example.esgithub.models.program.request.LikeRequest
import com.example.esgithub.models.reaction.ReactionType
import com.example.esgithub.ui.view.ProgramDetailsActivity
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_CREATED_AT
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_DESCRIPTION
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_ID
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_LIKES_SIZE
import com.example.esgithub.ui.view.adapters.ProgramsListAdapter.Companion.PROGRAM_SOURCE_CODE
import com.example.esgithub.ui.view.adapters.interfaces.OnProgramClicked
import com.example.esgithub.ui.view.adapters.interfaces.OnProgramLiked
import com.example.esgithub.ui.viewModels.ProgramViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(), OnProgramClicked, OnProgramLiked {
    private val programViewModel: ProgramViewModel by viewModel { parametersOf(requireContext()) }

    private lateinit var binding: FragmentHomeBinding

    private lateinit var programListRv: RecyclerView

    private var recyclerViewState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        parseAndInjectConfiguration()
        injectModuleDependencies(requireContext())

        this.programListRv = binding.programsRv

        this.setUpObservers()
    }

    private fun setUpObservers() {
        programViewModel.programsData.observe(viewLifecycleOwner) { programs ->
            val commentCounts = programViewModel.commentsCountsByProgram.value ?: emptyMap()
            setUpProgramList(programs, commentCounts)
        }
        programViewModel.commentsCountsByProgram.observe(viewLifecycleOwner) { commentCounts ->
            val programs = programViewModel.programsData.value ?: emptyList()
            setUpProgramList(programs, commentCounts)
        }
    }

    private fun setUpProgramList(
        programs: List<ProgramModel>,
        commentCounts: Map<String, Int>
    ) {
        programListRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        programListRv.adapter =
            ProgramsListAdapter(
                programs,
                commentCounts,
                this,
                this
            )
        restoreRecyclerViewState()
    }

    private fun saveRecyclerViewState() {
        recyclerViewState = programListRv.layoutManager?.onSaveInstanceState()
    }

    private fun restoreRecyclerViewState() {
        programListRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    override fun displayProgramDetails(programData: ProgramModel) {
        val intent = Intent(context, ProgramDetailsActivity::class.java)

        intent.putExtra(PROGRAM_CREATED_AT, programData.createdAt)
        intent.putExtra(PROGRAM_DESCRIPTION, programData.description)
        intent.putExtra(PROGRAM_LIKES_SIZE, programData.reactions.count { it.type == ReactionType.LIKE }.toString())
        intent.putExtra(PROGRAM_SOURCE_CODE, programData.sourceCode)
        intent.putExtra(PROGRAM_ID, programData.programId)

        startActivity(intent)
    }

    override fun addLikeToAProgram(likeRequest: LikeRequest) {
        saveRecyclerViewState()
        this.programViewModel.addLikeToAProgram(likeRequest)
    }
}
