package com.example.esgithub.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esgithub.R
import com.example.esgithub.models.program.Comment

class CommentListAdapter(private val commentList: List<Comment>) : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentListAdapter.CommentViewHolder {
        val commentView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_comment, parent, false)

        return CommentViewHolder(commentView)
    }

    override fun getItemCount() = commentList.count()

    override fun onBindViewHolder(
        holder: CommentListAdapter.CommentViewHolder,
        position: Int
    ) {
        val currentComment = commentList[position]

        holder.bind(currentComment)
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var commentTv: TextView
        private var userName: TextView
        private var publishedAtComment: TextView

        init {
            this.commentTv = itemView.findViewById(R.id.comment_tv)
            this.userName = itemView.findViewById(R.id.user_name_comment_tv)
            this.publishedAtComment = itemView.findViewById(R.id.publisheAt_comment_tv)
        }

        fun bind(comment: Comment) {
            commentTv.text = comment.content
            userName.text = comment.user.userName
            publishedAtComment.text = comment.createdAt
        }
    }
}
