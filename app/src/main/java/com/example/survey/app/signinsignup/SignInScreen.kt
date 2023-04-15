package com.example.survey.app.signinsignup


import android.provider.ContactsContract
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import androidx.compose.ui.unit.dp


@Composable
fun SignInScreen(
    email:String?,
    onSignInSubmitted:(email:String,password:String) -> Unit,
    onSignInAsGuest:() -> Unit,
    onNavUp: () -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackbarErrorText =  "feature not available"
    val snackbarActionLabel =  "dismiss"



}

@Composable
fun SignInContent(
    email:String?,
    onSignInSubmitted:(email:String,password:String) -> Unit
){
    Column(modifier = Modifier.fillMaxWidth()){
        val focusRequester = remember{ FocusRequester() }
        val emailState by rememberSaveable(stateSaver = EmailStateSaver){
            mutableStateOf(EmailState(email))
        }
        Email(
            emailState,
            onImeAction = { focusRequester.requestFocus() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val passwordState = remember { PasswordState() }
        val onSubmit = {
            if (emailState.isValid && passwordState.isValid){
                onSignInSubmitted(emailState.text, passwordState.text)
            }
        }

        Password(
            label = "Password",
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = { onSubmit() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSubmit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = emailState.isValid && passwordState.isValid
        ){
            Text(text = "Sign in")
        }

    }
}

@Composable
fun ErrorSnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
){
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier.padding(16.dp),
                content = {
                    Text(text = "", style = MaterialTheme.typography.bodyMedium)
                },
                action = {data.visuals.actionLabel?.let {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Dismiss", color = MaterialTheme.colorScheme.inversePrimary)
                    }
                }}
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}