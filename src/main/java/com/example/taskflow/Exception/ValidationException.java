package com.example.taskflow.Exception;

public class ValidationException extends Throwable {
    private CustomError customError;

    public static ValidationExceptionBuilder builder() {
        return new ValidationExceptionBuilder();
    }

    public CustomError getCustomError() {
        return this.customError;
    }

    public void setCustomError(final CustomError customError) {
        this.customError = customError;
    }

    public ValidationException(final CustomError customError) {
        this.customError = customError;
    }

    public static class ValidationExceptionBuilder {
        private CustomError customError;

        ValidationExceptionBuilder() {
        }

        public ValidationExceptionBuilder customError(final CustomError customError) {
            this.customError = customError;
            return this;
        }

        public ValidationException build() {
            return new ValidationException(this.customError);
        }

        public String toString() {
            return "ValidationException.ValidationExceptionBuilder(customError=" + this.customError + ")";
        }
    }
}
