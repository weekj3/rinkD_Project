package com.example.rinkdproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;

public class DrinkSearch extends AppCompatActivity{
    ListView listView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_search);

        ListViewAdapter adapter;

        adapter=new ListViewAdapter();

        listView=(ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        //리스트 뷰에 들어갈 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.juicy1),
                "딸기 바나나 쥬스", "JUICY") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.juicy2),
                "초코 바나나 쥬스", "JUICY") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.juicy3),
                "키위 쥬스", "JUICY") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.twosome1),
                "바닐라 쉐이크", "TWOSOME") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.twosome2),
                "초코 쉐이크", "TWOSOME") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.twosome3),
                "커피 쉐이크", "TWOSOME") ;

        EditText editTextFilter=(EditText)findViewById(R.id.editTextFilter);
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                String filterText=edit.toString();
                if(filterText.length()>0){
                    listView.setFilterText(filterText);
                }
                else{
                    listView.clearTextFilter();
                }
            }
        });
    }
}
