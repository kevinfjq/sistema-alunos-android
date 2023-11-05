package com.example.sistemalistview.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sistemalistview.R;
import com.example.sistemalistview.model.Aluno;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Aluno> itemList;

    public CustomAdapter(Context context, List<Aluno> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.textView);
        Aluno aluno = itemList.get(i);
        String itemText = aluno.getNome();
        textView.setText(itemText);

        view.setId(aluno.getId());

        return view;
    }
}
