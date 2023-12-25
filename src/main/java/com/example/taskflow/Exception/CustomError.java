package com.example.taskflow.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomError {
    private String field;
    private String message;

    public CustomError(String field, String message) {
        this.field = field;
        this.message = message;
    }
    public CustomErrorBuilder builder(){
        return new CustomErrorBuilder(this);
    }

    @Override
    public String toString() {
        return "CustomError{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public class CustomErrorBuilder{
        private CustomError customError;

        public CustomErrorBuilder(CustomError customError) {
            this.customError = customError;
        }
        public CustomErrorBuilder field(String field){
            customError.field = field;
            return this;
        }
        public CustomErrorBuilder message(String message){
            customError.field = field;
            return this;
        }

        public CustomError build(){
            return this.customError;
        }

        @Override
        public String toString() {
            return "CustomErrorBuilder{" +
                    "customError=" + customError +
                    '}';
        }
    }
}
