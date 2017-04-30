package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 03.04.2017.
 */

public class ItemMessage extends FrameLayout {

    private TextView tvMessage;
    private TextView tvSender;
    private TextView tvTime;
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
        tvMessage = (TextView) findViewById(R.id.text_view_message);
        tvSender = (TextView) findViewById(R.id.text_view_sender);
        tvTime = (TextView) findViewById(R.id.tv_time_message);
        containerBackground = (LinearLayout) findViewById(R.id.container_background_item_message);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ItemMessage);
        if (typedArray != null) {
            if (typedArray.getString(R.styleable.ItemMessage_message_text) != null) {
                setTextMessage(typedArray.getString(R.styleable.ItemMessage_message_text));
            }
            if (typedArray.getString(R.styleable.ItemMessage_sender_name) != null) {
                setTvSender(typedArray.getString(R.styleable.ItemMessage_sender_name));
            }
            setType(typedArray.getInteger(R.styleable.ItemMessage_message_type, TYPE_IN));
            typedArray.recycle();
        }
    }

    public void setTextMessage(String textMessage){
        tvMessage.setText(textMessage);
    }

    public void setTvSender(String senderName){
        tvSender.setText(senderName);
    }

    public void setTime (String time){
        tvTime.setText(time);
    }

    public void setType (int type){
        this.type = type;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) containerBackground.getLayoutParams();

        if (type == TYPE_IN){
            tvSender.setVisibility(VISIBLE);
            containerBackground.setBackgroundResource(R.drawable.in_message);
            lp.gravity = Gravity.START;
        }
        else if (type == TYPE_OUT){
            tvSender.setVisibility(GONE);
            containerBackground.setBackgroundResource(R.drawable.out_message);
            lp.gravity = Gravity.END;
        }
        else throw new IllegalArgumentException("Unknown type" + type);

        containerBackground.setLayoutParams(lp);
    }

    public int getType() {
        return type;
    }
}
