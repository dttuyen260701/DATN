package com.example.realestateapp.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.realestateapp.util.Constants.DefaultValue.MAP_INSTALL_REQUEST
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tuyen.dang on 5/20/2023.
 */

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
