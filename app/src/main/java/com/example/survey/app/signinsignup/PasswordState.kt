package com.example.survey.app.signinsignup

class PasswordState: TextFieldState(
    validator = ::isPasswordValid,
    errorFor = ::passwordValidationError
)

class ConfirmPasswordState(private val passwordState: PasswordState): TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun isPasswordValid(password: String): Boolean {
    return password.length > 3
}

private fun passwordValidationError(password: String): String {
    return "Invalid password"
}

private fun passwordConfirmationError(password: String): String {
    return "Passwords don't match"
}

