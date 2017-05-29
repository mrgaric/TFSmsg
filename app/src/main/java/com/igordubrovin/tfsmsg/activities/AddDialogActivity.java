package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.firebase.OnTransactionComplete;
import com.igordubrovin.tfsmsg.firebase.dialog.DialogRepository;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDialogActivity extends AppCompatActivity {

    @BindView(R.id.add_title)
    EditText etAddTitle;
    @BindView(R.id.add_desc)
    EditText etAddDesc;
    @BindView(R.id.add_dialog_btn)
    Button btnAddDialog;
    @Inject
    DialogRepository dialogRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_dialog_btn)
    void clickBtnAdd(){
        DateHelper dateHelper = new DateHelper();
        DialogItem dialogItem = new DialogItem();
        dialogItem.setDesc(etAddDesc.getText().toString());
        dialogItem.setTitle(etAddTitle.getText().toString());
        dialogItem.setTime(dateHelper.getCurrentTime());
        dialogItem.setDate(dateHelper.getCurrentDate());
        dialogRepository.addDialog(dialogItem, new OnTransactionComplete<Void>() {
            @Override
            public void onCommit(Void result) {
                Intent intent = new Intent();
                intent.putExtra(ProjectConstants.DIALOG_ITEM_INTENT, Parcels.wrap(dialogItem));
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onAbort(Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
