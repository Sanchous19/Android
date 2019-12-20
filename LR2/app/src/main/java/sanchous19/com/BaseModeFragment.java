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

import org.mariuszgromada.math.mxparser.Expression;

import static java.lang.Double.isNaN;

public class BaseModeFragment extends Fragment implements View.OnClickListener {
    private String[] keywords = {
        "sin", "cos", "tg", "ctg", "ln", "log10", "pi", "e", "sqrt",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_base_mode, container, false);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Button toScientificModeButton = view.findViewById(R.id.toScientificModeButton);
            if (BuildConfig.FLAVOR.equals("demoRelease") || BuildConfig.FLAVOR.equals("demoDebug")) {
                toScientificModeButton.setVisibility(View.GONE);
            }
            toScientificModeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int action = R.id.action_basic_to_scientific_mode;
                    Navigation.findNavController(view).navigate(action);
                }
            });
        }

        int[] buttonIds = {
                R.id.zeroButton, R.id.oneButton, R.id.twoButton, R.id.threeButton, R.id.fourButton,
                R.id.fiveButton, R.id.sixButton, R.id.sevenButton, R.id.eightButton,
                R.id.nineButton, R.id.plusButton, R.id.minusButton, R.id.multiplicationButton,
                R.id.divideButton, R.id.powerButton, R.id.equalButton, R.id.dotButton,
                R.id.deleteButton, R.id.clearButton,
        };

        for (int id : buttonIds) {
            view.findViewById(id).setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        TextView expressionTextView = getActivity().findViewById(R.id.expressionTextView);
        TextView answerTextView = getActivity().findViewById(R.id.answerTextView);
        String expression = expressionTextView.getText().toString();

        switch (v.getId()) {
            case R.id.equalButton:
                String answer = Calculate(expression);
                answerTextView.setText(answer);
                break;
            case R.id.deleteButton:
                if (!expression.isEmpty()) {
                    Boolean is_delete = false;
                    for (String keyword : keywords) {
                        if (expression.endsWith(keyword)) {
                            expression = expression.substring(0, expression.length() - keyword.length());
                            is_delete = true;
                            break;
                        }
                    }
                    if (!is_delete) {
                        expression = expression.substring(0, expression.length() - 1);
                    }
                    expressionTextView.setText(expression);
                }
                break;
            case R.id.clearButton:
                expressionTextView.setText("");
                break;
            default:
                String buttonText = ((Button)v).getText().toString();
                expression += buttonText;
                expressionTextView.setText(expression);
                break;
        }
    }

    private String Calculate(String expr) {
        Expression expression = new Expression(expr);
        double answer = expression.calculate();

        if (isNaN(answer)) {
            return "Wrong Expression";
        }
        String s = Double.toString(answer);
        if (s.length() > 1 && s.substring(s.length() - 2, s.length()).equals(".0")) {
            return s.substring(0, s.length() - 2);
        }
        else {
            return s;
        }
    }
}
