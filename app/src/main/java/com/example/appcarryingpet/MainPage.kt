package com.example.appcarryingpet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.appcarryingpet.ui.theme.AppCarryingPetTheme
import androidx.compose.ui.geometry.Size

class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCarryingPetTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(LocalDensity.current)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(density: Density) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var clickOffset by remember { mutableStateOf(Offset.Zero) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        val topAppBarSize = with(density) { 56.dp.toPx() }
                        clickOffset = Offset(topAppBarSize / 2, topAppBarSize / 2)
                        showDialog = true                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "VetVirtualPet",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
        content = { padding ->
            MainContent(Modifier.padding(padding))
        }
    )

    if (showDialog) {
        val dialogWidth = with(LocalDensity.current) { 300.dp.toPx() }
        val dialogHeight = with(LocalDensity.current) { 200.dp.toPx() }
        val dialogSize = Size(dialogWidth, dialogHeight)

        AnimatedVisibility(
            visible = showDialog,
            enter = scaleIn(
                initialScale = 0.3f,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = scaleOut(
                targetScale = 0.3f,
                animationSpec = tween(durationMillis = 500)
            ),
            modifier = Modifier
                .size(dialogWidth.dp, dialogHeight.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Menu") },
                text = {
                    Column {
                        Row {
                            TextButton(
                                onClick = {
                                    showDialog = false
                                    // Navegar para com.example.appcarryingpet.EditUserPage
                                    val intent = Intent(context, EditUserPage::class.java)
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit User")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Edit User")
                            }
                            TextButton(
                                onClick = {
                                    showDialog = false
                                    // Navegar de volta para a LoginPage
                                    val intent = Intent(context, LoginPage::class.java)
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign Out")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Sign Out")
                            }
                        }
                    }
                },
                confirmButton = {
                    // Empty confirm button
                }
            )
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Main Page!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        // Add more UI components as needed
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppCarryingPetTheme {
        MainScreen(LocalDensity.current)
    }
}
