package com.app.wallet;

import com.app.BikeApplication;

public class WalletPresenter implements WalletContract.Presenter {

    private WalletContract.View view;

    public WalletPresenter(WalletContract.View view) {
        this.view = view;
    }

    @Override
    public void onDeclareButtonClick(String cashAmount) {
        view.clearWalletError();
        if(cashAmount.equals(" ") || !validate(Float.valueOf(cashAmount))){
            view.showWalletError("Min. amount of cash: " + BikeApplication.getDb().bikeDao().getMinValueOfPrice()); }
            else {
            view.declareCashSuccessfully();
        }
    }

    private boolean validate(float declaredCashFloat) {
        return declaredCashFloat >= BikeApplication.getDb().bikeDao().getMinValueOfPrice();
    }

}
