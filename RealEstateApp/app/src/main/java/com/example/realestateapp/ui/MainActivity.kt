package com.example.realestateapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import com.example.realestateapp.BuildConfig
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemNotification
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.extension.createImageFile
import com.example.realestateapp.extension.getFileFromUri
import com.example.realestateapp.extension.sendNotification
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.notification.navigation.navigateToNotification
import com.example.realestateapp.ui.post.navigation.navigateToRecords
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.NotificationChannel.MESSAGE_CHANNEL
import com.example.realestateapp.util.Constants.NotificationChannel.POST_CHANNEL
import com.example.realestateapp.util.Constants.RequestNotification.MESSAGE_NOTIFICATION
import com.example.realestateapp.util.Constants.RequestNotification.POST_NOTIFICATION
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private var appState: RealEstateAppState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RealEstateAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Transparent
                ) {
                    appState = rememberRealEstateAppState()

                    viewModel.run {

                        val launcherActivity = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestMultiplePermissions(),
                            onResult = {
                                onGrantedPermission(it)
                            }
                        )

                        val imagePicker = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent(),
                            onResult = { uri ->
                                uri?.let {
                                    getFileFromUri(it)?.let { file ->
                                        uploadImage(file)
                                    }
                                }
                            }
                        )

                        val cameraLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.TakePicture(),
                            onResult = { hasData ->
                                if (hasData) {
                                    uri?.let {
                                        getFileFromUri(it)?.let { file ->
                                            uploadImage(file)
                                        }
                                    }
                                }
                            }
                        )

                        setListenNotification {
                            val childPostListEventListener = object : ChildEventListener {
                                override fun onChildAdded(
                                    dataSnapshot: DataSnapshot,
                                    previousChildName: String?
                                ) {
                                    dataSnapshot.getValue(ItemNotification::class.java)?.run {
                                        if (!read)
                                            sendNotification(
                                                title = getString(
                                                    R.string.notificationPostTitle,
                                                    idPost.toString()
                                                ),
                                                content = messenger,
                                                drawable = R.drawable.story_notification,
                                                responseCode = POST_NOTIFICATION,
                                                forNewChannel = POST_CHANNEL
                                            )
                                    }
                                }

                                override fun onChildChanged(
                                    dataSnapshot: DataSnapshot,
                                    previousChildName: String?
                                ) {
                                    dataSnapshot.getValue(ItemNotification::class.java)?.run {
                                        if (!read)
                                            sendNotification(
                                                title = getString(
                                                    R.string.notificationPostTitle,
                                                    idPost.toString()
                                                ),
                                                content = messenger,
                                                drawable = R.drawable.story_notification,
                                                responseCode = POST_NOTIFICATION,
                                                forNewChannel = POST_CHANNEL
                                            )
                                    }
                                }

                                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

                                override fun onChildMoved(
                                    dataSnapshot: DataSnapshot,
                                    previousChildName: String?
                                ) {
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            }

                            val childChannelListEventListener = object : ChildEventListener {
                                override fun onChildAdded(
                                    dataSnapshot: DataSnapshot,
                                    previousChildName: String?
                                ) {
                                    dataSnapshot.getValue(ItemChatGuest::class.java)?.run {
                                        if (!read)
                                            sendNotification(
                                                title = getString(
                                                    R.string.messageNotificationTitle,
                                                    nameGuest
                                                ),
                                                content = lastMessage,
                                                drawable = R.drawable.ic_message,
                                                responseCode = MESSAGE_NOTIFICATION,
                                                forNewChannel = MESSAGE_CHANNEL
                                            )
                                    }
                                }

                                override fun onChildChanged(
                                    dataSnapshot: DataSnapshot,
                                    previousChildName: String?
                                ) {
                                    dataSnapshot.getValue(ItemChatGuest::class.java)?.run {
                                        if (!read)
                                            sendNotification(
                                                title = getString(
                                                    R.string.messageNotificationTitle,
                                                    nameGuest
                                                ),
                                                content = lastMessage,
                                                drawable = R.drawable.ic_message,
                                                responseCode = MESSAGE_NOTIFICATION,
                                                forNewChannel = MESSAGE_CHANNEL
                                            )
                                    }
                                }

                                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

                                override fun onChildMoved(
                                    dataSnapshot: DataSnapshot,
                                    previousChildName: String?
                                ) {
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            }

                            getDataChild(Constants.FireBaseRef.CHANNEL_GUEST).child(it.toString())
                                .addChildEventListener(childChannelListEventListener)

                            getDataChild(Constants.FireBaseRef.CHANNEL_POST).child(it.toString())
                                .addChildEventListener(childPostListEventListener)
                        }

                        setUploadImageAndGetURL {
                            showDialog(
                                dialog = TypeDialog.ConfirmDialog(
                                    title = getString(R.string.choiceImageTitle),
                                    message = getString(R.string.choiceImageMessage),
                                    negativeBtnText = getString(R.string.btnGallery),
                                    onBtnNegativeClick = {
                                        imagePicker.launch("image/*")
                                    },
                                    positiveBtnText = getString(R.string.btnCamera),
                                    onBtnPositiveClick = {
                                        val file = createImageFile()
                                        uri = FileProvider.getUriForFile(
                                            Objects.requireNonNull(this@MainActivity),
                                            BuildConfig.APPLICATION_ID + ".provider", file
                                        )
                                        cameraLauncher.launch(uri)
                                    }
                                )
                            )
                        }

                        setRequestPermissionListener { permissions ->
                            val permissionsDeny = permissions.filter {
                                checkSelfPermission(it) == PackageManager.PERMISSION_DENIED
                            }
                            val permissionsAccept = permissions.filter {
                                checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
                            }
                            val resultAccept: MutableMap<String, Boolean> = mutableMapOf()
                            permissionsAccept.map {
                                resultAccept.put(it, true)
                            }
                            if (permissionsAccept.isNotEmpty()) {
                                onGrantedPermission(resultAccept)
                            }
                            if (permissionsDeny.isNotEmpty()) {
                                var title = ""
                                permissionsDeny.forEachIndexed { index, item ->
                                    title += ((if (index in 1 until permissionsDeny.size) ", " else "")
                                            + getPermissionTitle(item))
                                }
                                showDialog(
                                    dialog = TypeDialog.ConfirmDialog(
                                        message = getString(
                                            R.string.requestPermission,
                                            title
                                        ),
                                        negativeBtnText = getString(R.string.denyBtnTitle),
                                        onBtnNegativeClick = {},
                                        positiveBtnText = getString(R.string.acceptBtnTitle),
                                        onBtnPositiveClick = {
                                            launcherActivity.launch(permissionsDeny.toTypedArray())
                                        }
                                    )
                                )
                            }
                        }

                        val customTextSelectionColors = TextSelectionColors(
                            handleColor = RealEstateAppTheme.colors.primary,
                            backgroundColor = RealEstateAppTheme.colors.primary.copy(alpha = 0.4f)
                        )

                        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                            appState?.let {
                                RealEstateApp(
                                    appState = it,
                                    viewModel = this
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getPermissionTitle(permission: String) = when (permission) {
        Manifest.permission.CALL_PHONE -> Constants.PermissionTitle.PHONE
        Manifest.permission.ACCESS_COARSE_LOCATION -> Constants.PermissionTitle.COARSE_LOCATION
        Manifest.permission.ACCESS_FINE_LOCATION -> Constants.PermissionTitle.FINE_LOCATION
        Manifest.permission.WRITE_EXTERNAL_STORAGE -> Constants.PermissionTitle.WRITE_EXTERNAL
        Manifest.permission.READ_EXTERNAL_STORAGE -> Constants.PermissionTitle.READ_EXTERNAL
        Manifest.permission.POST_NOTIFICATIONS -> Constants.PermissionTitle.POST_NOTIFICATIONS
        Manifest.permission.CAMERA -> Constants.PermissionTitle.CAMERA
        else -> ""
    }

    @Override
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val result = intent.getIntExtra("KEY_SETTING_FRAG", 0)
        appState?.run {
            viewModel.run {
                when (result) {
                    MESSAGE_NOTIFICATION -> {
                        if (getUser().value != null) {
                            val topLevelNavOptions = navOptions {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                            navController.navigateToNotification(topLevelNavOptions)
                        }
                    }
                    POST_NOTIFICATION -> {
                        if (getUser().value != null)
                            navController.navigateToRecords(true)
                    }
                    else -> {}
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (
            viewModel.getDialogType().value is TypeDialog.ChoiceDataDialog
            || viewModel.getDialogType().value is TypeDialog.ShowImageDialog
        )
            viewModel.getDialogType().value = TypeDialog.Hide
        else {
            super.onBackPressed()
        }
    }
}
