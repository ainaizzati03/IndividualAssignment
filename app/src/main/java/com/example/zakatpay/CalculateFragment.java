package com.example.zakatpay;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalculateFragment extends Fragment {

    private EditText weightInput, valueInput;
    private RadioGroup typeGroup;
    private Button calculateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);

        weightInput = view.findViewById(R.id.weightInput);
        valueInput = view.findViewById(R.id.valueInput);
        typeGroup = view.findViewById(R.id.typeGroup);
        calculateButton = view.findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });

        return view;
    }

    private void calculateZakat() {
        String weightStr = weightInput.getText().toString().trim();
        String valueStr = valueInput.getText().toString().trim();
        int selectedTypeId = typeGroup.getCheckedRadioButtonId();

        // Validate if fields are empty
        if (TextUtils.isEmpty(weightStr)) {
            showError("Please enter the gold weight.");
            return;
        }

        if (TextUtils.isEmpty(valueStr)) {
            showError("Please enter the current gold value per gram.");
            return;
        }

        if (selectedTypeId == -1) {
            showError("Please select a gold type (Keep or Wear).");
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double value = Double.parseDouble(valueStr);

            // Validate if weight and value are positive
            if (weight <= 0) {
                showError("Gold weight must be a positive number.");
                return;
            }

            if (value <= 0) {
                showError("Gold value per gram must be a positive number.");
                return;
            }

            // Perform the zakat calculation
            double uruf = (selectedTypeId == R.id.radioKeep) ? 85 : 200;
            double payableWeight = Math.max(weight - uruf, 0);
            double zakatPayable = payableWeight * value;
            double totalZakat = zakatPayable * 0.025;

            // Display the results in the table
            ((TextView) getView().findViewById(R.id.weightValue)).setText(String.format("%.2f grams", weight));
            ((TextView) getView().findViewById(R.id.valueValue)).setText(String.format("RM %.2f", value));
            ((TextView) getView().findViewById(R.id.excessValue)).setText(String.format("%.2f grams", payableWeight));
            ((TextView) getView().findViewById(R.id.zakatPayableValue)).setText(String.format("RM %.2f", zakatPayable));
            ((TextView) getView().findViewById(R.id.totalZakatValue)).setText(String.format("RM %.2f", totalZakat));

            // Make the table visible
            getView().findViewById(R.id.outputTable).setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter valid numbers.");
        }
    }

    // Method to display error messages
    private void showError(String message) {
        // Hide the table if there is an error
        getView().findViewById(R.id.outputTable).setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
