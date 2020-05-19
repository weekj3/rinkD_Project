package com.example.rinkdproject.ui.drink;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rinkdproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//ICE탭을 보여주기 위한 프래그먼트1
//Expandablelistview를 추가한 xml 파일을 만들어준다.
public class FirstFragment extends Fragment {
    private RecyclerView recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fistfragment, container, false);

        //부모 리스트와 자식 리스트를 선언해준다.
        ArrayList<HashMap<String,String>> groupData = new ArrayList<>();
        ArrayList<ArrayList<HashMap<String,String>>> childData = new ArrayList<>();

        //부모 리스트에 들어갈 요소들을 추가해준다.
        HashMap<String,String> groupA = new HashMap<>();
        groupA.put("group","커피");
        HashMap<String,String> groupB = new HashMap<>();
        groupB.put("group","스무디");

        groupData.add(groupA);
        groupData.add(groupB);

        //자식 리스트에 들어갈 요소들을 추가해준다.
        ArrayList<HashMap<String,String>>childListA = new ArrayList<>();

        HashMap<String,String> childAA = new HashMap<>();
        childAA.put("group","커피");
        childAA.put("name", "아메리카노");
        childListA.add(childAA);

        HashMap<String,String> childAB = new HashMap<>();
        childAB.put("group","커피");
        childAB.put("name", "카페라떼");
        childListA.add(childAB);

        HashMap<String,String> childAC = new HashMap<>();
        childAC.put("group","커피");
        childAC.put("name", "카페모카");
        childListA.add(childAC);

        childData.add(childListA);

        ArrayList<HashMap<String,String>> childListB = new ArrayList<>();

        HashMap<String, String> childBA = new HashMap<>();
        childBA.put("group", "스무디");
        childBA.put("name", "딸기 스무디");
        childListB.add(childBA);

        HashMap<String, String> childBB = new HashMap<>();
        childBB.put("group", "스무디");
        childBB.put("name", "요거트 스무디");
        childListB.add(childBB);

        HashMap<String, String> childBC = new HashMap<>();
        childBC.put("group", "스무디");
        childBC.put("name", "복숭아 스무디");
        childListB.add(childBC);

        childData.add(childListB);

        // 부모, 자식 리스트들 포함한 Adapter 를 생성한다.
        SimpleExpandableListAdapter adapter  = new SimpleExpandableListAdapter(
                getContext(), groupData,android.R.layout.simple_expandable_list_item_1,
                new String[] {"group"},new int[] {android.R.id.text1},
                childData, android.R.layout.simple_expandable_list_item_2,new String[] {"name","group"},new int[]
                {android.R.id.text1,android.R.id.text2});

        //ExpandableListView에 생성한 Adapter를 설정한다.
        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        listView.setAdapter(adapter);




        return view;
    }



}