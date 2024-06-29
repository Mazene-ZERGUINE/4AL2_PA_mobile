package com.example.esgithub.ui.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esgithub.R
import com.example.esgithub.models.program.ProgramModel
import com.example.esgithub.models.reaction.ReactionType

class ProgramsListAdapter(
    private val programsList: List<ProgramModel>,
    private val commentCounts: Map<String, Int>,
    private val onProgramClicked: OnProgramClicked
) :
    RecyclerView.Adapter<ProgramsListAdapter.ProgramViewHolder>() {
    companion object {
        const val PROGRAM_ID = "program_id"
        const val PROGRAM_LIKES_SIZE = "program_likes_size"
        const val PROGRAM_CREATED_AT = "program_createdAt"
        const val PROGRAM_SOURCE_CODE = "program_source_code"
        const val PROGRAM_CREATED_BY = "program_createdBy"
        const val PROGRAM_DESCRIPTION = "program_description"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val programView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_program, parent, false)

        return ProgramViewHolder(programView)
    }

    override fun getItemCount() = programsList.count()

    override fun onBindViewHolder(
        holder: ProgramViewHolder,
        position: Int
    ) {
        val currentProgram = programsList[position]

        holder.itemView.setOnClickListener {
            onProgramClicked.displayProgramDetails(currentProgram)
        }

        holder.bind(currentProgram)
    }

    inner class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var productTitleTv: TextView
        private var publishedAtTv: TextView
        private var profileNameTv: TextView
        private var programCommentsSize: TextView
        private var programLikesSize: TextView

        init {
            productTitleTv = itemView.findViewById(R.id.productTitleTv)
            publishedAtTv = itemView.findViewById(R.id.productPublishedAt)
            profileNameTv = itemView.findViewById(R.id.profileNameTv)
            programCommentsSize = itemView.findViewById(R.id.comments_size_tv)
            programLikesSize = itemView.findViewById(R.id.likes_size_iv)
        }

        fun bind(programData: ProgramModel) {
            productTitleTv.text = programData.programmingLanguage
            publishedAtTv.text = programData.createdAt
            profileNameTv.text = programData.user.firstName
            programLikesSize.text = getLikesCountOfAProgram(programData).toString()
            Log.d("programListAdpaterComments", commentCounts[programData.programId].toString())
            programCommentsSize.text = commentCounts[programData.programId].toString()
        }

        private fun getLikesCountOfAProgram(programData: ProgramModel): Int  {
            Log.d("reactions", programData.reactions.toString())
            return programData.reactions.count {
                it.type == ReactionType.LIKE
            }
        }
    }
}
