package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageEditor extends LinearLayout{

    private EditText editTextMessage;
    private ImageView sendImage;
    private ImageView clearImage;

    private OnClickListener listenerSend;
    private OnClickListener listenerClear;

    private boolean flagAnimatedClear;

    public MessageEditor(@NonNull Context context) {
        super(context);
    }

    public MessageEditor(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public MessageEditor(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_message_editor, this);
        sendImage = (ImageView) findViewById(R.id.image_view_send);
        sendImage.setOnClickListener(clickSend);
        clearImage = (ImageView) findViewById(R.id.image_view_clear);
        clearImage.setOnClickListener(clickClear);
        editTextMessage = (EditText) findViewById(R.id.edit_text_message);
        editTextMessage.setOnTextEmptyListener(emptyEditTextListener);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MessageEditor);
        if (typedArray != null) {
            if (typedArray.getString(R.styleable.MessageEditor_hint) != null) {
                setHint(typedArray.getString(R.styleable.MessageEditor_hint));
            }
            if (typedArray.getString(R.styleable.MessageEditor_text) != null) {
                setText(typedArray.getString(R.styleable.MessageEditor_text));
            }
            setEnabledSend(typedArray.getBoolean(R.styleable.MessageEditor_enabled_send, false));
            setVisibilityClear(typedArray.getInteger(R.styleable.MessageEditor_visible_clear, INVISIBLE));
            setFlagAnimatedClear(typedArray.getBoolean(R.styleable.MessageEditor_flag_animated_clear, false));
            typedArray.recycle();
        }
    }

    public void setHint(String hint){
        editTextMessage.setHint(hint);
    }

    public void setEnabledSend(Boolean enabledSend){
        if (enabledSend) {
            sendImage.setImageResource(R.drawable.ic_send_color_accent);
        } else {
            sendImage.setImageResource(R.drawable.ic_send_color_gray);
        }
        sendImage.setClickable(enabledSend);
    }

    public void setVisibilityClear(int visibilityClear){
        Animation anim;
        switch (visibilityClear){
            case VISIBLE:
                clearImage.setVisibility(View.VISIBLE);
                anim = AnimationUtils.loadAnimation(getContext(), R.anim.clear_image_show);
                break;
            case INVISIBLE:
                clearImage.setVisibility(View.INVISIBLE);
                anim = AnimationUtils.loadAnimation(getContext(), R.anim.clear_image_hide);
                break;
            default: return;
        }
        if (flagAnimatedClear)
            clearImage.startAnimation(anim);
    }

    public void setFlagAnimatedClear(boolean flagAnimatedClear) {
        this.flagAnimatedClear = flagAnimatedClear;
    }

    public void setText(String text){
        editTextMessage.setText(text);
    }

    public String getText(){
        return editTextMessage.getText().toString();
    }

    public void setOnClickListenerSend(OnClickListener listenerSend){
        this.listenerSend = listenerSend;
    }

    public void setOnClickListenerClear(OnClickListener listenerClear){
        this.listenerClear = listenerClear;
    }

    OnClickListener clickSend = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listenerSend != null)
                listenerSend.onClick(v);
            setText("");
        }
    };

    OnClickListener clickClear = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listenerClear != null)
                listenerClear.onClick(v);
            setText("");
        }
    };

    private EditText.OnTextEmptyListener emptyEditTextListener = new EditText.OnTextEmptyListener() {
        @Override
        public void textIsNotEmpty() {
            if (clearImage.getVisibility() == View.INVISIBLE) {
                setEnabledSend(true);
                setVisibilityClear(VISIBLE);
            }
        }

        @Override
        public void textIsEmpty() {
            setEnabledSend(false);
            setVisibilityClear(INVISIBLE);
        }
    };
}
