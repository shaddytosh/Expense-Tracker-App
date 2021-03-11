package com.ofisini.expense.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ofisini.expense.R;
import com.ofisini.expense.adapters.SectionsPageAdapter;
import com.ofisini.expense.fragments.BalanceFragment;
import com.ofisini.expense.fragments.ExpenseFragment;
import com.ofisini.expense.utils.Constants;

public class ExpenseActivity extends AppCompatActivity {


    private ViewPager mViewPager;


    private ImageButton delete;

    public static FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        delete=(ImageButton)findViewById(R.id.delete);

        mViewPager=findViewById(R.id.container);
        setupViewPager(mViewPager);


        TabLayout tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new ExpenseDialog().show(getSupportFragmentManager(), "Dialog");

                Intent intent =new Intent(ExpenseActivity.this, AddExpenseActivity.class);
                intent.putExtra("from", Constants.addExpenseString);
                startActivity(intent);

            }
        });

    }




    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExpenseFragment(),"Expenses");
        adapter.addFragment(new BalanceFragment(),"Summary");
        viewPager.setAdapter(adapter);
    }

}
