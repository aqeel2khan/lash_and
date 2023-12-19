package com.cosmetics.core.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.cosmetics.core.R
import com.google.android.material.snackbar.Snackbar
import java.util.*

private const val TIME_FORMAT = "%02d:%02d"


fun showCommonAPISnackBar(
    message: String,
    actionText: String,
    view: View,
    retryCallback: (() -> Unit)?
): Snackbar? {
    try {
        var snackBar: Snackbar
        if (retryCallback != null) {
            snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(actionText) {
                retryCallback()
            }
        } else {
            snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        }
        showSnack(snackBar)
        return snackBar
    } catch (exception: Exception) {
        exception.fillInStackTrace()
    }
    return null
}

fun AppCompatActivity.showCancelableSnackBar(
    message: String,
    actionText: String,
    retryCallback: (() -> Unit)
) {
    try {
        var snackBar: Snackbar = Snackbar.make(
            this.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(actionText) {
            retryCallback()
        }
        showSnack(snackBar)
    } catch (exception: Exception) {
        exception.fillInStackTrace()
    }
}

fun FragmentActivity.showSnackBarLessTime(message: String) {
    try {
        var snackBar: Snackbar = Snackbar.make(
            this.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )
        showSnack(snackBar)
    } catch (exception: Exception) {
        exception.fillInStackTrace()
    }
}

fun showSnack(snackBar: Snackbar) {
    val snackBarView = snackBar.view
    val textView = snackBarView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.maxLines = 5
    textView.setTextColor(ContextCompat.getColor(snackBarView.context, R.color.white))
    snackBar.show()
}

fun Activity.callTelephone(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    if (intent.resolveActivity(packageManager) != null)
        startActivity(intent)
}

fun Activity.openMap(location: String) {
    val map = "http://maps.google.co.in/maps?q=$location"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
    if (intent.resolveActivity(packageManager) != null)
        startActivity(intent)
}

fun Activity.openEmail(addresses: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$addresses") // only email apps should handle this
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }

}


fun selectDate(view: View, selectedDate: (date: String) -> Unit) {
    val calBefore = Calendar.getInstance()
    val dialog = DatePickerDialog(
        view.context,
        R.style.MyAppThemeThree,
        DatePickerDialog.OnDateSetListener { dpView, year, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1
            val time = "$year-$month-$dayOfMonth"
            selectedDate(time)
        },
        calBefore.get(Calendar.YEAR),
        calBefore.get(Calendar.MONTH),
        calBefore.get(Calendar.DAY_OF_MONTH)
    )
    dialog.show()
}

fun selectTime(view: View, selectedTime: (date: String) -> Unit) {
    val context = view.context
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    val mTimePicker = TimePickerDialog(
        view.context,
        R.style.MyAppThemeThree,
        OnTimeSetListener { _, selectedHour, selectedMinute ->
            selectedTime(String.format(TIME_FORMAT, selectedHour, selectedMinute))
        }, hour, minute, true
    )
    mTimePicker.setTitle(context.getString(R.string.select_time))
    mTimePicker.show()

}