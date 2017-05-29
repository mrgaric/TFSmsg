package com.igordubrovin.tfsmsg.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Игорь on 29.05.2017.
 */

public class DialogIdManager {
    private List<String> dialogsId;

    public DialogIdManager(){
        dialogsId = new LinkedList<>();
    }

    public void addDialogId(String id){
        ((LinkedList)dialogsId).addFirst(id);
    }

    public String getDialogId(int position){
        return dialogsId.get(position);
    }

}
