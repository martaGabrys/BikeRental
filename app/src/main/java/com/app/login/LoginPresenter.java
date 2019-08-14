package com.app.login;

import com.app.R;


public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void onLoginButtonClick(String email, String password) {
        view.clearErrors();
        if (!validate(email)) {
            view.showEmailError(R.string.login_error_email);
        } else if (!validate(password)) {
            view.showPasswordError(R.string.login_error_password);
        } else {
            view.logIn();
        }
    }

    private boolean validate(String input) {
        return input.length() >= 5;
    }
}
