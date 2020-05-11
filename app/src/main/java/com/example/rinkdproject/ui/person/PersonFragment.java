package com.example.rinkdproject.ui.person;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import com.example.rinkdproject.R;

public class PersonFragment extends Fragment {

    ViewGroup viewGroup;

    Button bStart, bStop;                           // 버튼 설정

    ViewFlipper vf;                                //  뷰플립 설정

    @SuppressLint("WrongViewCast")
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View View= inflater.inflate(R.layout.fragment_person,container,false);

        final ViewFlipper vf = (ViewFlipper)View.findViewById(R.id.viewFlipper);

        vf.setFlipInterval(10);

        Button bStart = (Button)View.findViewById(R.id.bStart);
        bStart.setOnClickListener(
                new ImageButton.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), PersonFragment.class);

                        vf.startFlipping();                         // 지금 있는 그림의 이전 그림 보여주기
                    }
                });

        Button bStop = (Button)View.findViewById(R.id.bStop);
        bStop.setOnClickListener(
                new ImageButton.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PersonFragment.class);

                        vf.stopFlipping();               // 지금 있는 그림의 다음 그림 보여주기
                    }
                }
        );

        return View;

    }
}