import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import otus.gpb.recyclerview.R
import otus.gpb.recyclerview.models.Chat

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lastMessage = itemView.findViewById<TextView>(R.id.LastMessage)
        private val time = itemView.findViewById<TextView>(R.id.Time)
        private val verified = itemView.findViewById<ImageView>(R.id.Verified)
        private val scam = itemView.findViewById<TextView>(R.id.Scam)
        private val mute = itemView.findViewById<ImageView>(R.id.Mute)

        fun bind(chat: Chat) {
            val title = itemView.findViewById<TextView>(R.id.Title)
            val statusIcon = itemView.findViewById<ImageView>(R.id.Delivered)
            val avatarImageView = itemView.findViewById<ImageView>(R.id.Avatar)
            avatarImageView.setImageResource(chat.avatarRes)
            title.text = chat.title
            lastMessage.text = chat.lastMessage
            time.text = chat.time
            verified.visibility = if (chat.isVerified) View.VISIBLE else View.GONE
            scam.visibility = if (chat.isScam) View.VISIBLE else View.GONE
            mute.visibility = if (chat.isMuted) View.VISIBLE else View.GONE
            if (chat.isOutgoing) {
                statusIcon.visibility = View.VISIBLE
                val resId = if (chat.isRead) R.drawable.ic_status_read else R.drawable.ic_status_delivered
                statusIcon.setImageResource(resId)
            } else {
                statusIcon.visibility = View.GONE
            }
        }
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}