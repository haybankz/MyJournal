package com.haybankz.myjournal.data;

import android.content.Context;

import com.haybankz.myjournal.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleAuthHelper {

    private static GoogleSignInOptions mGoogleSignInOptions;
    private static GoogleSignInClient mGoogleSignInClient;


    public static GoogleSignInClient getClient(Context context){

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, mGoogleSignInOptions);

        return mGoogleSignInClient;
    }
}
