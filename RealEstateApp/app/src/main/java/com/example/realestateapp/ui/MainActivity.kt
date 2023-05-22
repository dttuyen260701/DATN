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
                            contract = ActivityResultContracts.RequestPermission(),
                            onResult = {
                                if (it) {
                                    onGrantedPermission()
                                }
                            }
                        )

                        setRequestPermissionListener {
                            if (checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED) {
                                onGrantedPermission()
                            } else {
                                val title = when (it) {
                                    Manifest.permission.CALL_PHONE -> Constants.PermissionTitle.PHONE
                                    else -> null
                                }
                                title?.let { titlePermission ->
                                    showDialog(
                                        dialog = TypeDialog.ConfirmDialog(
                                            message = getString(
                                                R.string.requestPermission,
                                                titlePermission
                                            ),
                                            negativeBtnText = getString(R.string.denyBtnTitle),
                                            onBtnNegativeClick = {},
                                            positiveBtnText = getString(R.string.acceptBtnTitle),
                                            onBtnPositiveClick = {
                                                launcherActivity.launch(it)
                                            }
                                        )
                                    )
                                }
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
}
