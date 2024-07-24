import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.esgithub.R

class NotifierService(
    private val context: Context
) {
    fun showErrorToast(message: String) {
        showToast(message, R.layout.toast_error)
    }

    fun showSuccessToast(message: String) {
        showToast(message, R.layout.toast_success)
    }

    private fun showToast(
        message: String,
        layoutId: Int
    ) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = inflater.inflate(layoutId, null)
        val textView: TextView = layout.findViewById(R.id.toastMessage)
        textView.text = message

        with(Toast(context)) {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}
