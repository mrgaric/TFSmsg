package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 03.04.2017.
 */

public class ItemMessage extends LinearLayout {

    private TextView message;
    private TextView sender;
    private LinearLayout containerBackground;

    private int type;

    public static final int TYPE_IN = 0x00000000;
    public static final int TYPE_OUT = 0x00000001;

    public ItemMessage(Context context) {
        super(context);
    }

    public ItemMessage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public ItemMessage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_item_message, this);
        message = (TextView) findViewById(R.id.text_view_message);
        sender = (TextView) findViewById(R.id.text_view_sender);
        containerBackground = (LinearLayout) findViewById(R.id.container_background_item_message);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ItemMessage);
        if (typedArray != null) {
            if (typedArray.getString(R.styleable.ItemMessage_message_text) != null) {
                setTextMessage(typedArray.getString(R.styleable.ItemMessage_message_text));
            }
            if (typedArray.getString(R.styleable.ItemMessage_sender_name) != null) {
                setSender(typedArray.getString(R.styleable.ItemMessage_sender_name));
            }
            setType(typedArray.getInteger(R.styleable.ItemMessage_message_type, 0x00000000));
            typedArray.recycle();
        }
    }

    public void setTextMessage(String textMessage){
        message.setText(textMessage);
    }

    public void setSender (String senderName){
        sender.setText(senderName);
    }

    public void setType (int type){
        this.type = type;
        if (type == TYPE_IN){
            sender.setVisibility(VISIBLE);
            containerBackground.setBackgroundResource(R.drawable.in_message);
        } else if (type == TYPE_OUT){
            sender.setVisibility(GONE);
            containerBackground.setBackgroundResource(R.drawable.out_message);
        } else throw new IllegalArgumentException("Unknown type" + type);
    }

    public int getType() {
        return type;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        setLayoutParams(params);
    }
}
