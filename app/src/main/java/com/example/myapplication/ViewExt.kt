import android.content.res.Resources
import android.view.View

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