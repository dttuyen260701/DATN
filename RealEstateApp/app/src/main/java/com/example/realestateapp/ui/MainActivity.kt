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
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

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
