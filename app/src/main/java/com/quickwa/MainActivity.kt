package com.quickwa

import android.content.ActivityNotFoundException
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import com.quickwa.ui.theme.QuickWhatsAppMessageTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickWhatsAppMessageTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppRoot()
                }
            }
        }
    }
}

private enum class Screen {
    Splash,
    Main,
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AppRoot() {
    var screen by remember { mutableStateOf(Screen.Splash) }

    LaunchedEffect(Unit) {
        delay(650) // short, not slow
        screen = Screen.Main
    }

    AnimatedContent(
        targetState = screen,
        transitionSpec = {
            fadeIn(animationSpec = tween(180)) togetherWith fadeOut(animationSpec = tween(120))
        },
        label = "screen",
    ) { target ->
        when (target) {
            Screen.Splash -> SplashScreen()
            Screen.Main -> MainScreen()
        }
    }
}

@Composable
private fun SplashScreen() {
    val accent = MaterialTheme.colorScheme.primary
    val accent2 = MaterialTheme.colorScheme.tertiary
    val bg = Brush.linearGradient(listOf(accent.copy(alpha = 0.20f), accent2.copy(alpha = 0.10f), Color.Transparent))

    var animate by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        animationSpec = tween(durationMillis = 220),
        label = "splashAlpha",
    )
    val scale by animateFloatAsState(
        targetValue = if (animate) 1f else 0.92f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "splashScale",
    )

    LaunchedEffect(Unit) { animate = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.alpha(alpha).scale(scale)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(88.dp),
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var rawInput by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val accent = MaterialTheme.colorScheme.primary
    val accent2 = MaterialTheme.colorScheme.tertiary
    val bg = Brush.verticalGradient(
        listOf(
            accent.copy(alpha = 0.14f),
            accent2.copy(alpha = 0.08f),
            Color.Transparent,
        ),
    )

    val hasClipboardText = rememberClipboardHasText(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(34.dp),
                    )
                    Spacer(Modifier.size(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(R.string.title), style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = stringResource(R.string.subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    if (rawInput.isNotBlank()) {
                        TextButton(
                            onClick = {
                                rawInput = ""
                                error = null
                                focusManager.clearFocus()
                            },
                        ) {
                            Text(stringResource(R.string.clear))
                        }
                    }
                }

                val focused = remember { mutableStateOf(false) }
                val focusScale by animateFloatAsState(
                    targetValue = if (focused.value) 1.01f else 1f,
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "focusScale",
                )

                OutlinedTextField(
                    value = rawInput,
                    onValueChange = {
                        rawInput = it
                        if (error != null) error = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(focusScale)
                        .onFocusChanged { focused.value = it.isFocused },
                    singleLine = true,
                    isError = error != null,
                    placeholder = { Text(stringResource(R.string.hint_paste_phone)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        errorContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    trailingIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (hasClipboardText && rawInput.isBlank()) {
                                IconButton(
                                    onClick = {
                                        val pasted = readClipboardText(context)
                                        if (!pasted.isNullOrBlank()) rawInput = pasted
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ContentPaste,
                                        contentDescription = stringResource(R.string.paste),
                                    )
                                }
                            }
                            if (rawInput.isNotBlank()) {
                                IconButton(onClick = { rawInput = "" }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = stringResource(R.string.clear),
                                    )
                                }
                            }
                        }
                    },
                )

                if (error != null) {
                    Text(
                        text = error.orEmpty(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = stringResource(R.string.examples_label),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = "${stringResource(R.string.example_1)}   ${stringResource(R.string.example_2)}   ${stringResource(R.string.example_3)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val pressScale by animateFloatAsState(
                    targetValue = if (isPressed) 0.985f else 1f,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium),
                    label = "pressScale",
                )

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        val normalized = JordanPhone.normalizeToInternational(rawInput)
                        if (normalized == null) {
                            error = context.getString(R.string.invalid_jo_number)
                            return@Button
                        }
                        error = null
                        openWhatsAppChat(context, normalized)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .scale(pressScale),
                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                    interactionSource = interactionSource,
                ) {
                    Text(stringResource(R.string.open_in_whatsapp))
                }
            }
        }
    }
}

private fun openWhatsAppChat(context: Context, internationalNumberWithoutPlus: String) {
    val url = "https://wa.me/$internationalNumberWithoutPlus"
    val uri = Uri.parse(url)

    // Prefer WhatsApp if installed; otherwise fall back to any browser.
    val whatsappIntent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.whatsapp")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    val pm = context.packageManager
    val canOpenWhatsApp = whatsappIntent.resolveActivity(pm) != null
    try {
        if (canOpenWhatsApp) {
            context.startActivity(whatsappIntent)
        } else {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    } catch (_: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}

@Composable
private fun rememberClipboardHasText(context: Context): Boolean {
    val clipboard = remember { context.getSystemService<ClipboardManager>() }
    val hasText = remember { mutableStateOf(false) }

    LaunchedEffect(clipboard) {
        hasText.value = clipboard?.hasPrimaryClip() == true &&
            (clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == true ||
                clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML) == true)
    }

    return hasText.value
}

private fun readClipboardText(context: Context): String? {
    val clipboard = context.getSystemService<ClipboardManager>() ?: return null
    val clip = clipboard.primaryClip ?: return null
    if (clip.itemCount <= 0) return null
    return clip.getItemAt(0).coerceToText(context)?.toString()
}

