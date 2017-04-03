package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 03.04.2017.
 */

public class ItemMessage extends LinearLayout {

    private TextView message;
    private TextView sender;
    private LinearLayout rootItem;
    private LinearLayout containerBackground;

    private int type;

    public static final int TYPE_OUT = 1;
    public static final int TYPE_IN = 2;

    public ItemMessage(Context context) {
        super(context);
    }

    public ItemMessage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemMessage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttrs(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_item_message, this);
        message = (TextView) findViewById(R.id.text_view_message);
        sender = (TextView) findViewById(R.id.text_view_sender);
        rootItem = (LinearLayout) findViewById(R.id.root_item_message);
        containerBackground = (LinearLayout) findViewById(R.id.container_background_item_message);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MessageEditor);
        if (typedArray != null) {
            if (typedArray.getString(R.styleable.ItemMessage_text_message) != null) {
                setTextMessage(typedArray.getString(R.styleable.ItemMessage_text_message));
            }
            if (typedArray.getString(R.styleable.ItemMessage_sender) != null) {
                setSender(typedArray.getString(R.styleable.ItemMessage_sender));
            }
            setType(typedArray.getInt(R.styleable.ItemMessage_type, 1));
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
            rootItem.setGravity(Gravity.LEFT);
            containerBackground.setBackgroundResource(R.drawable.in_message);
        } else if (type == TYPE_OUT){
            sender.setVisibility(GONE);
            rootItem.setGravity(Gravity.RIGHT);
            containerBackground.setBackgroundResource(R.drawable.out_message);
        } else throw new IllegalArgumentException("Unknown type" + type);
    }

    public int getType() {
        return type;
    }
}
