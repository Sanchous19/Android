package sanchous19.com;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScientificModeFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_scientific_mode, container, false);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Button toBaseModeButton = view.findViewById(R.id.toBaseModeButton);
            toBaseModeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int action = R.id.action_scientific_to_basic_mode;
                    Navigation.findNavController(view).navigate(action);
                }
            });
        }

        int[] buttonIds = {
                R.id.sinButton, R.id.cosButton, R.id.tgButton, R.id.ctgButton, R.id.lgButton,
                R.id.lnButton, R.id.piButton, R.id.eButton, R.id.sqrtButton, R.id.openBracketButton,
                R.id.closeBracketButton, R.id.factorialButton,
        };

        for (int id : buttonIds) {
            view.findViewById(id).setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        TextView expressionTextView = getActivity().findViewById(R.id.expressionTextView);
        String expression = expressionTextView.getText().toString();
        
        switch (v.getId()) {
            case R.id.lgButton:
                expression += "log10(";
                expressionTextView.setText(expression);
                break;
            case R.id.piButton:
                expression += "pi";
                expressionTextView.setText(expression);
                break;
            case R.id.eButton:
                expression += "e";
                expressionTextView.setText(expression);
                break;
            case R.id.sqrtButton:
                expression += "sqrt(";
                expressionTextView.setText(expression);
                break;
            case R.id.openBracketButton:
                expression += "(";
                expressionTextView.setText(expression);
                break;
            case R.id.closeBracketButton:
                expression += ")";
                expressionTextView.setText(expression);
                break;
            case R.id.factorialButton:
                expression += "!";
                expressionTextView.setText(expression);
                break;
            default:
                String buttonText = ((Button)v).getText().toString();
                expression += buttonText + "(";
                expressionTextView.setText(expression);
                break;
        }

    }

}
