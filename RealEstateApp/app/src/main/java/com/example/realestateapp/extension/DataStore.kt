package com.example.realestateapp.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.realestateapp.util.Constants

/**
 * Created by tuyen.dang on 5/11/2023.
 */

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DataStore.NAME)

internal suspend fun Context.readStoreLauncher(
    onReadSuccess: (String, String) -> Unit,
    onErrorAction: () -> Unit
) {
    val keyEmail = stringPreferencesKey(Constants.DataStore.KEY_EMAIL)
    val keyPassword = stringPreferencesKey(Constants.DataStore.KEY_PASSWORD)
    this.dataStore.data.collect {
        val email = it[keyEmail] ?: ""
        val pass = it[keyPassword] ?: ""
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            onReadSuccess(email, pass)
        } else {
            onErrorAction()
        }
    }
}

internal suspend fun Context.writeStoreLauncher(email: String, password: String) {
    val keyEmail = stringPreferencesKey(Constants.DataStore.KEY_EMAIL)
    val keyPassword = stringPreferencesKey(Constants.DataStore.KEY_PASSWORD)
    this.dataStore.edit { preferences ->
        preferences[keyEmail] = email
        preferences[keyPassword] = password
    }
}
