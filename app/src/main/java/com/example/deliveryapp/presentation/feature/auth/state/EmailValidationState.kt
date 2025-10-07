package com.example.deliveryapp.presentation.feature.auth.state

data class EmailValidationState(
    val isValid: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        fun validate(email: String): EmailValidationState {
            return when {
                email.isEmpty() -> EmailValidationState(
                    isValid = false,
                    errorMessage = "Email cannot be empty"
                )
                !isValidEmailFormat(email) -> EmailValidationState(
                    isValid = false,
                    errorMessage = "Enter a valid email"
                )
                else -> EmailValidationState(
                    isValid = true,
                    errorMessage = null
                )
            }
        }

        private fun isValidEmailFormat(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}