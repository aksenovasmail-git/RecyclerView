package otus.gpb.recyclerview.models

data class Chat(
    val id: Int,
    val title: String,
    val lastMessage: String,
    val time: String,
    val isScam: Boolean = false,
    val isMuted: Boolean = false,
    val isOutgoing: Boolean,
    val avatar: String? = null,
    val isVerified: Boolean = false,
    val isRead: Boolean = false,
    val avatarRes: Int,

)