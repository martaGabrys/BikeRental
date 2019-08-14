package com.app.wallet;

public interface WalletContract {

    interface View{

        void showWalletError(String walletError);

        void declareCashSuccessfully ();

        void clearWalletError();

    }

    interface Presenter {

        void onDeclareButtonClick(String declaredCash);

    }


}
