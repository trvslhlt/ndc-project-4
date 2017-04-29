package com.travisandjersy.safephoto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travisandjersy.safephoto.service.AuthenticationService;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class SigninFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signin_view, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this is here as a mock authentication action
        // can remove at any time
        AuthenticationService.setAuthenticationToken(null);
    }
}
