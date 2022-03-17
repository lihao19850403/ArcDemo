package com.lihao.en_base.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int fragmentID) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentID, fragment);
        transaction.commit();
    }
}
