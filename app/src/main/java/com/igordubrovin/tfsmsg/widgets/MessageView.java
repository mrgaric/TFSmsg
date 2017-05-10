package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageView extends ConstraintLayout {

    private TextView tvMessage;
    private TextView tvSender;
    private TextView tvTime;
    private TextView tvDate;
    private LinearLayout containerBackground;
    private ConstraintLayout constraintLayout;

    private int type;

    public static final int TYPE_IN = 0x00000000;
    public static final int TYPE_OUT = 0x00000001;
    public static final int VISIBLE_DATE = 0x00000002;
    public static final int GONE_DATE = 0x00000003;
    public static final int INVISIBLE_DATE = 0x00000004;

    public MessageView(Context context) {
        super(context);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_message_view, this);
        tvMessage = (TextView) findViewById(R.id.text_view_message);
        tvSender = (TextView) findViewById(R.id.text_view_sender);
        tvTime = (TextView) findViewById(R.id.tv_time_message);
        tvDate = (TextView) findViewById(R.id.tv_date_message);
        containerBackground = (LinearLayout) findViewById(R.id.container_background_item_message);
        constraintLayout = (ConstraintLayout) findViewById(R.id.item_message_parent);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MessageView);
        if (typedArray != null) {
            if (typedArray.getString(R.styleable.MessageView_message_text) != null) {
                setTextMessage(typedArray.getString(R.styleable.MessageView_message_text));
            }
            if (typedArray.getString(R.styleable.MessageView_sender_name) != null) {
                setTvSender(typedArray.getString(R.styleable.MessageView_sender_name));
            }
            if (typedArray.getString(R.styleable.MessageView_time) != null){
                setTvTime(typedArray.getString(R.styleable.MessageView_time));
            }
            if (typedArray.getString(R.styleable.MessageView_date) != null){
                setTvDate(typedArray.getString(R.styleable.MessageView_date));
            }
            setVisibilityTvDate(typedArray.getInt(R.styleable.MessageView_date_visibility, VISIBLE_DATE));
            setType(typedArray.getInteger(R.styleable.MessageView_message_type, TYPE_IN));
            typedArray.recycle();
        }
    }

    public void setTextMessage(String textMessage){
        tvMessage.setText(textMessage);
    }

    public void setTvSender(String senderName){
        tvSender.setText(senderName);
    }

    public void setTvTime (String time){
        tvTime.setText(time);
    }

    public void setTvDate (String date){
        tvDate.setText(date);
    }

    public void setVisibilityTvDate(int visibility){
        switch (visibility){
            case VISIBLE_DATE:
                tvDate.setVisibility(View.VISIBLE);
                break;
            case INVISIBLE_DATE:
                tvDate.setVisibility(View.INVISIBLE);
                break;
            case GONE_DATE:
                tvDate.setVisibility(View.GONE);
                break;
            default:
                tvDate.setVisibility(View.GONE);
                break;
        }
    }

    public void setType (int type){
        this.type = type;
        ConstraintLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topToBottom = tvDate.getId();
        if (type == TYPE_IN){
            tvSender.setVisibility(View.VISIBLE);
            containerBackground.setBackgroundResource(R.drawable.in_message);
            layoutParams.leftToLeft = LayoutParams.PARENT_ID;
        } else if (type == TYPE_OUT){
            tvSender.setVisibility(View.GONE);
            containerBackground.setBackgroundResource(R.drawable.out_message);
            layoutParams.rightToRight = LayoutParams.PARENT_ID;
        }
        else throw new IllegalArgumentException("Unknown type" + type);
        containerBackground.setLayoutParams(layoutParams);
    }

    public int getType() {
        return type;
    }
}
