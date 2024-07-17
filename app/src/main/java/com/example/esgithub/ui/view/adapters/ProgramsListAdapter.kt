package com.example.esgithub.ui.view.adapters

import android.media.session.MediaSession.Token
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esgithub.R
import com.example.esgithub.models.program.ProgramModel
import com.example.esgithub.models.program.request.LikeRequest
import com.example.esgithub.models.reaction.ReactionType
import com.example.esgithub.ui.view.adapters.interfaces.OnProgramClicked
import com.example.esgithub.ui.view.adapters.interfaces.OnProgramLiked
import com.example.esgithub.utils.TokenManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ProgramsListAdapter(
    private val programsList: List<ProgramModel>,
    private val commentCounts: Map<String, Int>,
    private val onProgramClicked: OnProgramClicked,
    private val onProgramLiked: OnProgramLiked
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

        Log.d("ProgramsListAdapter", "Binding program: ${currentProgram.user.firstName}")

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
        private var userProgramAvatar: ImageView
        private var likeButton: ImageButton
        private var programDescription: TextView

        private val imageBaseUrl: String = "http://"

        init {
            productTitleTv = itemView.findViewById(R.id.productTitleTv)
            publishedAtTv = itemView.findViewById(R.id.productPublishedAt)
            profileNameTv = itemView.findViewById(R.id.profileNameTv)
            programCommentsSize = itemView.findViewById(R.id.comments_size_tv)
            programLikesSize = itemView.findViewById(R.id.likes_size_iv)
            userProgramAvatar = itemView.findViewById(R.id.profilePicture)
            likeButton = itemView.findViewById(R.id.like_btn)
            programDescription = itemView.findViewById(R.id.programDescription)
        }

        fun bind(programData: ProgramModel) {
            productTitleTv.text = programData.programmingLanguage
            publishedAtTv.text = programData.createdAt
            profileNameTv.text = programData.user.firstName
            programLikesSize.text = getLikesCountOfAProgram(programData).toString()
            programCommentsSize.text = commentCounts[programData.programId].toString()
            programDescription.text = programData.description
            programData.user.avatarUrl?.let { this.setUserAvatar(it) }

            Log.d("ProgramsListAdapter", "Program user: ${programData.user.firstName}")

            if (getUserReaction(programData)) {
                likeButton.setImageResource(R.drawable.baseline_thumb_up_24)
            } else {
                likeButton.setImageResource(R.drawable.ic_like)
            }

            likeButton.setOnClickListener {
                val likeRequest = LikeRequest(programData.programId, TokenManager.getUserIdFromToken())
                onProgramLiked.addLikeToAProgram(likeRequest)
            }
        }

        private fun getLikesCountOfAProgram(programData: ProgramModel): Int {
            return programData.reactions.count { it.type == ReactionType.LIKE }
        }

        private fun getUserReaction(programData: ProgramModel): Boolean {
            val userId = TokenManager.getUserIdFromToken()
            Log.d("user", programData.user.firstName)
            if (programData.user.firstName == "Tom") {
                Log.d("prog", programData.reactions.toString())
            }
            return programData.reactions.any { it.user.userId == userId && it.type == ReactionType.LIKE }
        }

        private fun setUserAvatar(avatarLink: String) {
            Picasso.get()
                .load("$imageBaseUrl/$avatarLink")
                .into(userProgramAvatar, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception?) {
                        Log.e("Picasso", "Error loading image: ${e?.message}")
                    }
                })
        }
    }
}