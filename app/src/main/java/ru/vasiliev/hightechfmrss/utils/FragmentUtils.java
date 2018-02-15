package ru.vasiliev.hightechfmrss.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vasiliev on 14/02/2018.
 */

public class FragmentUtils {
    public static void replaceWithHistory(AppCompatActivity activity, Fragment fragment,
            int containerViewId) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public static void replaceWithHistory(FragmentManager fragmentManager, Fragment fragment,
            int containerViewId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }
}
