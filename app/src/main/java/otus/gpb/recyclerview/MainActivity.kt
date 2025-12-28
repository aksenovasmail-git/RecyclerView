package otus.gpb.recyclerview

import ChatAdapter
import CustomDividerItemDecoration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import otus.gpb.recyclerview.models.Chat
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    private val chatAdapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        val dividerItemDecoration = CustomDividerItemDecoration(this, R.drawable.divider_line)
        recyclerView.addItemDecoration(dividerItemDecoration)

        setupSwipe(recyclerView)

        chatAdapter.submitList(getMockData())
    }

    private fun setupSwipe(recyclerView: RecyclerView) {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentList = chatAdapter.currentList.toMutableList()
                currentList.removeAt(position)
                chatAdapter.submitList(currentList)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                if (dX < 0) {
                    paint.color = "#66A9E0".toColorInt()
                    val background = RectF(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    c.drawRect(background, paint)

                    if (Math.abs(dX) > 100) {
                        val icon =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_archive)
                        icon?.let {
                            val iconSize = (itemView.height * 0.37).toInt()
                            val iconMarginRight = 58

                            val iconTop = itemView.top + (itemView.height / 2) - iconSize
                            val iconBottom = iconTop + iconSize
                            val iconRight = itemView.right - iconMarginRight
                            val iconLeft = iconRight - iconSize

                            it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                            it.setTint(Color.WHITE)
                            it.draw(c)

                            paint.color = Color.WHITE
                            paint.textSize = 28f
                            paint.isAntiAlias = true
                            paint.textAlign = Paint.Align.CENTER

                            val textX = (iconLeft + iconRight) / 2f
                            val textY = iconBottom + 33f
                            c.drawText("Archive", textX, textY, paint)
                        }
                    }
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getMockData(): List<Chat> {
        return listOf(
            Chat(1, "Elon", "I love X", "14:00",  isScam = false,isMuted =false, isOutgoing = false,avatarRes = R.drawable.avatar_elon),
            Chat(2, "OTUS Bacic-android-2025-07", "Иди делать домашку", "Wed",  isScam = false,isMuted =false,isOutgoing = false,avatarRes = R.drawable.avatar_otus),
            Chat(3, "Arnold", "I'll be back", "14:45", isVerified = true, isOutgoing = false,avatarRes = R.drawable.avatar_arnold),
            Chat(4, "Telegram Support", "Login code: 123", "12:00", isVerified = true, isRead = true, isOutgoing = true,avatarRes = R.drawable.avatar_telega),
            Chat(5, "Crypto King", "Give me money", "Yesterday", isScam = true, isRead = false, isOutgoing = true,avatarRes = R.drawable.avatar_bitcoin),
            Chat(6, "Классная гусеница", "Ок", "10:00", isMuted = true, isOutgoing = false,avatarRes = R.drawable.avatar_cool)
        )
    }
}
