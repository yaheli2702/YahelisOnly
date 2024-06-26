package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {
// מטרת המחלקה היא לנהל ולהציג את הפרגמנטים השונים.
    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        //מטרת הפעולה היא לאתחל הכל.
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //מטרת הפעולה היא להחזיר את הפרגמנט בהתאם למיקום שנבחר.
        switch (position) {

            case 0:
                return new Home();
            case 1:
                return new FindTrip();
            case 2:
                return new AddTrip();
            case 3:
                return new Profil();
        }
        return new Home();

    }

    @Override
    public int getItemCount() {
        //מטרת הפעולה להחזיר את מספר הפרגמנטים הכולל שיש.
        return 4;
    }
}
