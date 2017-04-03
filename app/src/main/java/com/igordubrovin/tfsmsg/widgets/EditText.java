package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by Игорь on 21.03.2017.
 */

public class EditText extends android.widget.EditText {

    private OnTextEmptyListener textEmptyListener;

    public EditText(Context context) {
        super(context);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (textEmptyListener != null)
        {
            if (text.length() > 0)
                textEmptyListener.textIsNotEmpty();
            else
                textEmptyListener.textIsEmpty();
        }
    }

    public void setOnTextEmptyListener(OnTextEmptyListener listener){
        textEmptyListener = listener;
    }

    public interface OnTextEmptyListener{
        void textIsNotEmpty();
        void textIsEmpty();
    }
}
