package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressButton extends FrameLayout {

    @BindView(R.id.view_background)
    View background;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_progress_button, this);
        ButterKnife.bind(this, view);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.grey), PorterDuff.Mode.SRC_ATOP);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        if (typedArray != null) {
            if (typedArray.getString(R.styleable.ProgressButton_text) != null) {
                setText(typedArray.getString(R.styleable.ProgressButton_text));
            }
            setEnabled(typedArray.getBoolean(R.styleable.ProgressButton_enabled, true));
            typedArray.recycle();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        background.setEnabled(enabled);
        textView.setEnabled(enabled);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void showProgress() {
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        this.setClickable(false);
    }

    public void hideProgress() {
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        this.setClickable(true);
    }
}
