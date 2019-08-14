package com.app.login;

public interface LoginContract {

    interface View {
        void showEmailError(int errorMessageId);

        void showPasswordError(int errorMessageId);

        void logIn();

        void clearErrors();
    }

    interface Presenter {

        void onLoginButtonClick(String email, String password);
    }

}
