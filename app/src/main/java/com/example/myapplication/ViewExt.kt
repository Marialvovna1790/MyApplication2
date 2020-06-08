import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.example.myapplication.CircleDrawable

@Suppress("UNCHECKED_CAST")
fun <T : View?> View.view(viewId: Int, onClickListener: ((View) -> Unit)? = null): T {
    val view = findViewById<T>(viewId)
    onClickListener?.let { view?.setOnClickListener(it) }
    return view
}

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().getDisplayMetrics().density).toInt()
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().getDisplayMetrics().density).toInt()
}
fun bindColors(black: Pair<ImageView, Long>,
               red: Pair<ImageView, Long>,
               blue: Pair<ImageView, Long>,
               yellow: Pair<ImageView, Long>) {
    black.first.background = CircleDrawable(Color.BLACK, black.second)
    blue.first.background = CircleDrawable(Color.parseColor("#08088A"), blue.second)
    red.first.background = CircleDrawable(Color.parseColor("#8A0808"), red.second)
    yellow.first.background = CircleDrawable(Color.parseColor("#F4F558"), yellow.second)
}
