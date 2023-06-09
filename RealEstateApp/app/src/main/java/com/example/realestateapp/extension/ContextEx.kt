@file:Suppress("DEPRECATION")

package com.example.realestateapp.extension

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.realestateapp.R
import com.example.realestateapp.ui.MainActivity
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants.DefaultValue.CHANNEL_ID
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.MAP_INSTALL_REQUEST
import com.example.realestateapp.util.Constants.NotificationChannel.DEFAULT_CHANNEL
import com.example.realestateapp.util.Constants.RequestNotification.DEFAULT_NOTIFICATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tuyen.dang on 5/20/2023.
 */

internal fun Context.getFullAddress(
    coroutineScope: CoroutineScope,
    onStart: () -> Unit,
    onSuccess: (String) -> Unit
) {
    PickAddressViewModel.run {
        val streetName =
            if (streetChosen.value != DEFAULT_ITEM_CHOSEN) "${streetChosen.value.name}, " else ""
        val wardName =
            if (wardChosen.value != DEFAULT_ITEM_CHOSEN) "${wardChosen.value.name}, " else ""
        coroutineScope.launch(Dispatchers.IO) {
            onStart()
            val coder = Geocoder(this@getFullAddress, Locale.getDefault())
            val address: ArrayList<Address>?

            try {
                address =
                    coder.getFromLocationName(
                        "$detailStreet $streetName$wardName${districtChosen.value.name}",
                        1
                    ) as ArrayList<Address>?
                address?.firstOrNull()?.run {
                    PickAddressViewModel.let {
                        it.longitude = this.longitude
                        it.latitude = this.latitude
                    }
                }
            } catch (ex: IOException) {
                PickAddressViewModel.let {
                    it.longitude = 0.0
                    it.latitude = 0.0
                }
            }
            onSuccess("${detailStreet.value} $streetName$wardName${districtChosen.value.name}")
        }
    }
}

@SuppressLint("MissingPermission")
internal fun Context.sendNotification(
    title: String,
    content: String,
    drawable: Int,
    responseCode: Int = DEFAULT_NOTIFICATION,
    forNewChannel: Int = DEFAULT_CHANNEL
) {
    val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)

    val intent = Intent(
        this,
        MainActivity::class.java
    )

    intent.action = Intent.ACTION_MAIN
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.putExtra("KEY_SETTING_FRAG", responseCode)
    val pendingIntent = PendingIntent.getActivity(
        this, 1,
        intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    builder.setSmallIcon(R.drawable.ic_launcher_foreground)
    val bitmap = BitmapFactory.decodeResource(resources, drawable)
    builder.setLargeIcon(bitmap)
    builder.setContentTitle(title)
    builder.setAutoCancel(true)
    builder.setContentText(content)
    builder.setContentIntent(pendingIntent)
    NotificationManagerCompat.from(this).notify(
        if (forNewChannel == DEFAULT_CHANNEL)
            Calendar.getInstance().timeInMillis.toInt() else
            forNewChannel, builder.build()
    )
}

internal fun Context.openMap(latitude: Double, longitude: Double) {
    //String uri = "http://maps.google.com/maps/dir/?api=1&destination=" + latitude + "%2C-" + longitude + " (" + "Your partner is here" + ")";
    //String uri = "http://maps.google.com/maps/dir/?api=1&query=" + latitude + "%2C-" + longitude + " (" + "Your partner is here" + ")";
    //String uri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Your partner is here" + ")";
    //String uri = "https://maps.googleapis.com/maps/api/directions/json?origin="+latLng1.latitude+","+latLng1.longitude+"&destination="+latitude+","+longitude+"  &key=AIzaSyCaIgerehFWzBZuERI0lkVpp3y-fZIz94s";
    val uri =
        "http://maps.google.com/maps?daddr=$latitude,$longitude"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    try {
        startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        try {
            val unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(unrestrictedIntent)
        } catch (innerEx: ActivityNotFoundException) {
            makeToast(MAP_INSTALL_REQUEST)
        }
    }
}

internal fun Context.callPhone(phone: String) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:$phone")
    startActivity(callIntent)
}

internal fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
}

internal fun Context.getFileFromUri(uri: Uri): File? {
    val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val file = File(filePath, "IMG_${System.currentTimeMillis()}.jpg")
    try {
        contentResolver.openInputStream(uri)?.let { inputStream ->
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            while (true) {
                val len = inputStream.read(buf)
                if (len == -1) break
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        }
    } catch (e: Exception) {
        return null
    }

    return file
}
