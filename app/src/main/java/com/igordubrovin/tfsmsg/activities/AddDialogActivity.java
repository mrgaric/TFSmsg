package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.di.components.AddDialogScreenComponent;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IAddDialogPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IAddDialogView;
import com.igordubrovin.tfsmsg.mvp.presenters.AddDialogPresenter;
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

public class AddDialogActivity extends MvpActivity<IAddDialogView, IAddDialogPresenter>
        implements IAddDialogView{

    @BindView(R.id.add_title)
    EditText etAddTitle;
    @BindView(R.id.add_desc)
    EditText etAddDesc;
    @BindView(R.id.add_dialog_btn)
    Button btnAddDialog;
    @Inject
    AddDialogPresenter addDialogPresenter;

    private AddDialogScreenComponent addDialogScreenComponent = App.plusAddDialogScreenComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addDialogScreenComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public IAddDialogPresenter createPresenter() {
        return addDialogPresenter;
    }

    @OnClick(R.id.add_dialog_btn)
    void clickBtnAdd(){
        DateHelper dateHelper = new DateHelper();
        DialogItem dialogItem = new DialogItem();
        dialogItem.setDesc(etAddDesc.getText().toString());
        dialogItem.setTitle(etAddTitle.getText().toString());
        dialogItem.setTime(dateHelper.getCurrentTime());
        dialogItem.setDate(dateHelper.getCurrentDate());
        getPresenter().addDialog(dialogItem);
    }

    @Override
    public void dialogAdded(DialogItem dialogItem) {
        Intent intent = new Intent();
        intent.putExtra(ProjectConstants.DIALOG_ITEM_INTENT, Parcels.wrap(dialogItem));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    }
}
