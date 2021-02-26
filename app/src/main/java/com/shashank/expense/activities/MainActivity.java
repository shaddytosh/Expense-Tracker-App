package com.shashank.expense.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.expense.R;
import com.shashank.expense.adapters.SectionsPageAdapter;
import com.shashank.expense.fragments.BalanceFragment;
import com.shashank.expense.fragments.CustomBottomSheetDialogFragment;
import com.shashank.expense.fragments.ExpenseFragment;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private ViewPager mViewPager;


    private ImageButton delete;

    public static FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        delete=(ImageButton)findViewById(R.id.delete);

        mViewPager=findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomBottomSheetDialogFragment().show(getSupportFragmentManager(), "Dialog");

            }
        });

    }




    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExpenseFragment(),"Expenses");
        adapter.addFragment(new BalanceFragment(),"Balance");
        viewPager.setAdapter(adapter);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        inflater.inflate(R.menu.card_action, popup.getMenu());
        popup.show();
    }
    /*public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.card_action);
        popup.show();
    }*/

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:

                return true;
            case R.id.delete:

                showDeleteConfirmationDialog();
                return true;
            default:
                return false;
        }
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the product.
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {
        Uri mCurrentContactUri = null;
        if (mCurrentContactUri != null) {
            // Call the ContentResolver to delete the product at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentProductUri
            // content URI already identifies the product that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentContactUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the ctivity
    }


}
