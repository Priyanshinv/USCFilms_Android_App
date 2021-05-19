package com.example.uscfilms;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    private MoviesFragment moviesFragment;
    private TVShowsFragment tvShowsFragment;
    public LinearLayout linearLayout;
    //public BottomNavigationView bottomNavigationView;

    public HomeFragment(BottomNavigationView bottomNavigationView) {
        //this.bottomNavigationView=bottomNavigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        linearLayout = v.findViewById(R.id.linearLayout);
        moviesFragment = new MoviesFragment(linearLayout);
        tvShowsFragment = new TVShowsFragment(linearLayout);
        getChildFragmentManager().beginTransaction().replace(R.id.home_fragment_container, moviesFragment).commit();
        Button button = (Button)v.findViewById(R.id.moviesButton);
        Button button2 = (Button)v.findViewById(R.id.tvShowsButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                button2.setTextColor(ContextCompat.getColor(getContext(),R.color.blue));
                getChildFragmentManager().beginTransaction().replace(R.id.home_fragment_container,moviesFragment).commit();
            }
        });
        /*Button button1 = (Button)v.findViewById(R.id.uscfilms);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                button2.setTextColor(ContextCompat.getColor(getContext(),R.color.blue));
                getChildFragmentManager().beginTransaction().replace(R.id.home_fragment_container,moviesFragment).commit();
            }
        });*/

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("My activity","Clicked tv shows");
                button2.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                button.setTextColor(ContextCompat.getColor(getContext(),R.color.blue));
                getChildFragmentManager().beginTransaction().replace(R.id.home_fragment_container,tvShowsFragment).commit();
            }
        });
        return v;
    }
}
