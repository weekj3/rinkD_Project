package com.example.rinkdproject.ui.drink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.fragment.app.Fragment;

import com.example.rinkdproject.R;

import java.util.ArrayList;
import java.util.HashMap;

//HOT탭의 화면을 보여주기 위한 프래그먼트2
//Expandablelistview를 추가한 xml 파일을 만들어준다.
public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_secondfragment, container, false);

        //FirstFragment.java 파일과 마찬가지로 부모와 자식 리스트를 생성한 뒤
        //각각 필요한 요소들을 추가해주고
        //Adapter를 생성하여
        //ExpandableListView에 넣어준다.
        ArrayList<HashMap<String, String>> groupData = new ArrayList<>();
        ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<>();

        HashMap<String, String> groupA = new HashMap<>();
        groupA.put("group", "커피");
        HashMap<String, String> groupB = new HashMap<>();
        groupB.put("group", "차");

        groupData.add(groupA);
        groupData.add(groupB);

        ArrayList<HashMap<String, String>> childListA = new ArrayList<>();

        HashMap<String, String> childAA = new HashMap<>();
        childAA.put("group", "커피");
        childAA.put("name", "아메리카노");
        childListA.add(childAA);

        HashMap<String, String> childAB = new HashMap<>();
        childAB.put("group", "커피");
        childAB.put("name", "카페라떼");
        childListA.add(childAB);

        HashMap<String, String> childAC = new HashMap<>();
        childAC.put("group", "커피");
        childAC.put("name", "카페모카");
        childListA.add(childAC);

        childData.add(childListA);

        ArrayList<HashMap<String, String>> childListB = new ArrayList<>();

        HashMap<String, String> childBA = new HashMap<>();
        childBA.put("group", "차");
        childBA.put("name", "유자차");
        childListB.add(childBA);

        HashMap<String, String> childBB = new HashMap<>();
        childBB.put("group", "차");
        childBB.put("name", "녹차");
        childListB.add(childBB);

        HashMap<String, String> childBC = new HashMap<>();
        childBC.put("group", "차");
        childBC.put("name", "페퍼민트");
        childListB.add(childBC);

        childData.add(childListB);

        SimpleExpandableListAdapter adapter2 = new SimpleExpandableListAdapter(
                getContext(), groupData, android.R.layout.simple_expandable_list_item_1,
                new String[]{"group"}, new int[]{android.R.id.text1},
                childData, android.R.layout.simple_expandable_list_item_2, new String[]{"name", "group"}, new int[]
                {android.R.id.text1, android.R.id.text2});

        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.expandableListView2);
        listView.setAdapter(adapter2);


        return view;
    }

}
