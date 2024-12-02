package com.example.zakatpay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    // Factory method to create a new instance of this fragment
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve any passed parameters (if necessary)
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set up the button to navigate to the Calculate Zakat screen
        Button calculateZakatButton = view.findViewById(R.id.calculateZakatButton);
        calculateZakatButton.setOnClickListener(v -> {
            // Replace the current fragment with the CalculateFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new CalculateFragment())
                    .addToBackStack(null) // Add the transaction to the back stack so the user can navigate back
                    .commit();
        });

        return view;
    }
}
