package com.ofisini.expense.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ofisini.expense.R;
import com.ofisini.expense.activities.AddExpenseActivity;
import com.ofisini.expense.utils.Constants;

public class ExpenseDialog extends BottomSheetDialogFragment {

    Button addExpenseButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plus_expense, container, false);

        addExpenseButton=v.findViewById(R.id.bottom_sheet_expense_btn2);



        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent =new Intent(getActivity(), AddExpenseActivity.class);
                intent.putExtra("from", Constants.addExpenseString);
                startActivity(intent);

            }
        });


        return v;
    }
}