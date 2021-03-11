package com.ofisini.expense.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ofisini.expense.R;
import com.ofisini.expense.adapters.CustomAdapter;
import com.ofisini.expense.transactionDb.AppDatabase;
import com.ofisini.expense.transactionDb.AppExecutors;
import com.ofisini.expense.transactionDb.TransactionEntry;
import com.ofisini.expense.transactionDb.TransactionViewModel;
import com.ofisini.expense.utils.Constants;
import com.ofisini.expense.utils.ExpenseList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ExpenseFragment extends Fragment {

    private static final String LOG_TAG = ExpenseFragment.class.getSimpleName();

    private RecyclerView rv;
    private List<TransactionEntry> transactionEntries;
    private CustomAdapter customAdapter;

    public TransactionViewModel transactionViewModel;

    private AppDatabase mAppDb;

    private TextView balanceTv;
    private TextView dateTv;

    private int balanceAmount,incomeAmount,expenseAmount;
    private int mealExpense,cloudExpense,miscellaneousExpense;

    long firstDate;

    ArrayList<ExpenseList> expenseList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_expense,container,false);
        rv=view.findViewById(R.id.transactionRecyclerView);
        rv.setHasFixedSize(true);
        transactionEntries = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));






        mAppDb = AppDatabase.getInstance(getContext());

        mAppDb = AppDatabase.getInstance(getContext());

        balanceTv = view.findViewById(R.id.totalAmountTextView2);

        dateTv = view.findViewById(R.id.dateTextView2);
        expenseList=new ArrayList<>();
        getAllBalanceAmount();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                // COMPLETED (1) Get the diskIO Executor from the instance of AppExecutors and
                // call the diskIO execute method with a new Runnable and implement its run method

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();

                        List<TransactionEntry> transactionEntries = customAdapter.getTransactionEntries();
                        mAppDb.transactionDao().removeExpense(transactionEntries.get(position));

                    }
                });

                Snackbar.make(view,"Transaction Deleted",Snackbar.LENGTH_LONG).show();
            }

        }).attachToRecyclerView(rv);

        setupViewModel();


        return view;
    }
               /* new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.delete_this_list))
                        .setMessage(R.string.delete_list_message)
                        .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id1) {

                                int position = viewHolder.getAdapterPosition();
                                List<TransactionEntry> transactionEntries = customAdapter.getTransactionEntries();
                                mAppDb.transactionDao().removeExpense(transactionEntries.get(position));
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id12) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }*/


    private void getAllBalanceAmount(){

        //get date when first transaction date and todays date
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                firstDate=mAppDb.transactionDao().getFirstDate();
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String first = df.format(new Date(firstDate));
        Date today= Calendar.getInstance().getTime();
        String todaysDate=df.format(today);
        String Date=first+" - "+todaysDate;
        dateTv.setText(Date);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int income = mAppDb.transactionDao().getAmountByTransactionType(Constants.incomeCategory);
                incomeAmount = income;
                int expense = mAppDb.transactionDao().getAmountByTransactionType(Constants.expenseCategory);
                expenseAmount = expense;
                int balance = income - expense;
                balanceAmount = balance;
            }
        });
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                balanceTv.setText(String.valueOf(balanceAmount)+"  Ksh");

            }
        });


    }

    public void setupViewModel(){
        transactionViewModel = ViewModelProviders.of(this)
                .get(TransactionViewModel.class);

        transactionViewModel.getExpenseList()
                .observe(this, new Observer<List<TransactionEntry>>() {
                    @Override
                    public void onChanged(@Nullable List<TransactionEntry> transactionEntriesFromDb) {
                        Log.i(LOG_TAG,"Actively retrieving from DB");


                        transactionEntries = transactionEntriesFromDb;
//                        Logging to check DB values
                        for (int i =0 ; i < transactionEntries.size() ; i++){
                            String description = transactionEntries.get(i).getDescription();
                            int amount = transactionEntries.get(i).getAmount();
                            //Log.i("DESCRIPTION AMOUNT",description + String.valueOf(amount));
                        }

//                        Setting Adapter
                        customAdapter=new CustomAdapter(getActivity(),transactionEntries);
                        rv.setAdapter(customAdapter);
                    }
                });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }
}
