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

public class IncomeDialog extends BottomSheetDialogFragment {

    Button addIncomeButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plus_income, container, false);

        addIncomeButton=v.findViewById(R.id.bottom_sheet_income_btn);

        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent =new Intent(getActivity(), AddExpenseActivity.class);
                intent.putExtra("from", Constants.addIncomeString);
                startActivity(intent);
            }
        });



        return v;
    }
}