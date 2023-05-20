package com.example.realestateapp.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.realestateapp.util.Constants.DefaultValue.MAP_INSTALL_REQUEST

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

internal fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
