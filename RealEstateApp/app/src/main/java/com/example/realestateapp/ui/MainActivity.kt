package com.example.realestateapp.ui

import android.Manifest
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
import com.example.realestateapp.BuildConfig
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.extension.createImageFile
import com.example.realestateapp.extension.getFileFromUri
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RealEstateAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Transparent
                ) {
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
                            RealEstateApp(viewModel = this)
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
        Manifest.permission.CAMERA -> Constants.PermissionTitle.CAMERA
        else -> ""
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
