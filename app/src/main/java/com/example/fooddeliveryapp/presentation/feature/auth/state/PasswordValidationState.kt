package com.example.fooddeliveryapp.presentation.feature.auth.state

data class PasswordValidationState(
    val isValid: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        private val PASSWORD_REGEX =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,12}$")
        fun validate(password: String): PasswordValidationState {
            return when {
                password.isEmpty() -> PasswordValidationState(
                    isValid = false,
                    errorMessage = "Password cannot be empty"
                )
                !PASSWORD_REGEX.matches(password) -> PasswordValidationState(
                    isValid = false,
                    errorMessage = "Password must be 6-12 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character"
                )
                else -> PasswordValidationState(
                    isValid = true,
                    errorMessage = null
                )
            }
        }
    }
}