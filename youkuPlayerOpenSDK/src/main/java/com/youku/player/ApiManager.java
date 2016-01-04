package com.youku.player;

import com.youku.login.activity.LoginRegistCardViewDialogActivity;
import com.youku.login.base.YoukuLoginOperator;

import android.content.Context;


public class ApiManager extends ApiBaseManager {

    /**
     * 调用YoukuLoginSDK的登陆功能
     */
    public static void doLogin(Context context) {
        YoukuLoginOperator.initYoukuLogin(context);
        YoukuLoginOperator.showLoginView(context,
                LoginRegistCardViewDialogActivity.KEY_FAVOR_VID, LoginRegistCardViewDialogActivity.INTENT_FAVORITE_ACTIVITY
        );
    }
}
