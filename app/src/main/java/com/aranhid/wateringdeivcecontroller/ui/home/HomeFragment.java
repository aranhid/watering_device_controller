package com.aranhid.wateringdeivcecontroller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aranhid.wateringdeivcecontroller.GoogleAuth;
import com.aranhid.wateringdeivcecontroller.LastValues;
import com.aranhid.wateringdeivcecontroller.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    TextView helloTextView, lastCheckTextView, lastHumidityTextView, lastWateringTextView;

    GoogleAuth googleAuth;
    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        helloTextView = root.findViewById(R.id.helloTextView);
        lastCheckTextView = root.findViewById(R.id.lastCheckValueTextEdit);
        lastHumidityTextView = root.findViewById(R.id.lastHumidityValueTextView);
        lastWateringTextView = root.findViewById(R.id.lastWateringValueTextView);

        googleAuth = new GoogleAuth(root.getContext());

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (firebaseUser == null) {
            if (googleAuth.checkLogIn()) {
                firebaseUser = googleAuth.getCurrentFirebaseUser();

                helloTextView.setText(String.format("Привет, %s!", firebaseUser.getDisplayName()));

                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(firebaseUser.getUid()).child("lastValues").addValueEventListener(new LastValuesListener());
            }
        }
    }

    private void updateTextViews(LastValues lastValues){
        lastCheckTextView.setText(lastValues.getFormattedHumidityCheckTime());
        lastHumidityTextView.setText(lastValues.lastHumidity.toString());
        lastWateringTextView.setText(lastValues.getFormattedWateringTime());
    }

    class LastValuesListener  implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            LastValues lastValues = snapshot.getValue(LastValues.class);
            updateTextViews(lastValues);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}