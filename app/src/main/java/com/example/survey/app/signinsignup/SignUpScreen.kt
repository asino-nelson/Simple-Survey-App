package com.example.survey.app.signinsignup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    email:String?,
    onSignUpSubmitted:(email:String,password:String) -> Unit,
    onSignInAsGuest:() -> Unit,
    onNavUp: () -> Unit
){
    Scaffold(
        topBar = {
            SignInSignUpTopBar(topAppBarText = "Sign up", onNavUp = onNavUp)
        },
        content = { contentPadding ->
            SignInSignUpScreen(
                //modifier = Modifier.supprtWideScreen(),
                onSignInAsGuest = onSignInAsGuest,
                contentPadding = contentPadding
            ) {
                Column {
                    SignUpContent(
                        email = email,
                        onSignUpSubmitted = onSignUpSubmitted
                    )
                }
            }
        }
    )
}



@Composable
fun SignUpContent(
    email: String?,
    onSignUpSubmitted: (email: String, password: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }

        val emailState = remember { EmailState(email) }
        val passwordState = remember { PasswordState() }
        val confirmPasswordState = remember { ConfirmPasswordState(passwordState) }


        Email(
            emailState,
            onImeAction = {passwordFocusRequest.requestFocus()}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Password(
            label = "Password",
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = {confirmationPasswordFocusRequest.requestFocus()},
            modifier = Modifier.focusRequester(passwordFocusRequest)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Password(
            label = "Confirm password",
            passwordState = confirmPasswordState,
            onImeAction = {onSignUpSubmitted(emailState.text, passwordState.text)},
            modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Accept Terms and Conditions", color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSignUpSubmitted(emailState.text,passwordState.text) },
            modifier = Modifier.fillMaxWidth(),
            enabled = emailState.isValid && passwordState.isValid && confirmPasswordState.isValid
        ) {
            Text(text = "Create Account")
        }



    }
}