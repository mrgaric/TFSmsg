package com.igordubrovin.tfsmsg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageView extends RelativeLayout {

    @BindView(R.id.text_view_message)
    TextView tvMessage;
    @BindView(R.id.text_view_sender)
    TextView tvSender;
    @BindView(R.id.tv_time_message)
    TextView tvTime;
    @BindView(R.id.tv_date_message)
    TextView tvDate;
    @BindView(R.id.container_background_item_message)
    LinearLayout containerBackground;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_message_view, this);
        ButterKnife.bind(this, view);

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
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (type == TYPE_IN){
            tvSender.setVisibility(View.VISIBLE);
            containerBackground.setBackgroundResource(R.drawable.in_message);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (type == TYPE_OUT){
            tvSender.setVisibility(View.GONE);
            containerBackground.setBackgroundResource(R.drawable.out_message);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        else throw new IllegalArgumentException("Unknown type" + type);
        layoutParams.addRule(RelativeLayout.BELOW, tvDate.getId());
        containerBackground.setLayoutParams(layoutParams);
    }

    public int getType() {
        return type;
    }
}
