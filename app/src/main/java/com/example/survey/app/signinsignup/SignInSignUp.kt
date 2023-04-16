package com.example.survey.app.signinsignup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SignInSignUpScreen(
    onSignInAsGuest: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable () -> Unit
){
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ){
        item{
            Spacer(modifier = Modifier.height(44.dp))

            Box(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                content()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OrSignInAsGuest(
                onSignInAsGuest = onSignInAsGuest,
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun SignInSignUpTopBar(
    topAppBarText: String,
    onNavUp: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = topAppBarText,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        }
    )
}

@Composable
fun Email(
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit
){
    OutlinedTextField(
        value = emailState.text,
        onValueChange = {
            emailState.text = it
        },

        label = {
            Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
        },

        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },

        textStyle = MaterialTheme.typography.bodyMedium,

        isError = emailState.enableShowErrors(),

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),

        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        )

    )

    emailState.getError()?.let { error ->
        TextFieldError(textError = error)
    }
}

@Composable
fun Password(
    label:String,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit
){
    val showPassword = rememberSaveable { mutableSetOf(false) }

    OutlinedTextField(
        value = passwordState.text,
        onValueChange = { it ->
            passwordState.text = it
            passwordState.enableShowErrors()
        },

        label = {
            Text(text = "Password", style = MaterialTheme.typography.bodyMedium)
        },

        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                passwordState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    passwordState.enableShowErrors()
                }
            },

        trailingIcon = {
                       if (showPassword.value){
                           IconButton(onClick = { showPassword.value = false }) {
                               Icon(imageVector = Icons.Filled.Close, contentDescription = "Hide password")
                           }else
                               IconButton(onClick = { showPassword.value = true }) {
                                   Icon(imageVector = Icons.Filled.Check, contentDescription = "Show password")
                               }

                       }
        },

        textStyle = MaterialTheme.typography.bodyMedium,

        isError = passwordState.enableShowErrors(),

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),

        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        )

    )
}

@Composable
fun TextFieldError(textError:String){
    Row(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Error",
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun OrSignInAsGuest(
    onSignInAsGuest: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "or",
            modifier.paddingFromBaseline(top = 25.dp)
        )
        OutlinedButton(
            onClick = onSignInAsGuest,
            modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 24.dp)
        ) {
            Text(text = "Sign in as guest")
        }
    }
}