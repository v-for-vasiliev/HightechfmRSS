package ru.vasiliev.hightechfmrss.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

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
