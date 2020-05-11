package com.example.rinkdproject.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.example.rinkdproject.MapsActivity;
import com.example.rinkdproject.R;

public class HomeFragment extends Fragment {

    ViewGroup viewGroup;

    ViewFlipper flipper;

    ToggleButton toggle_Flipping;                 //자동 Flipping 선택

    @SuppressLint("WrongViewCast")
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //viewGroup=(ViewGroup)inflater.inflate(R.layout.fragment_home,container,false);
        //return viewGroup;

        View View= inflater.inflate(R.layout.fragment_home,container,false);

        Button buttongps = (Button)View.findViewById(R.id.buttongps);
        buttongps.setOnClickListener(
                new ImageButton.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), MapsActivity.class);
                        startActivity(intent);

                    }
                }

        );

        final ViewFlipper flipper = (ViewFlipper)View.findViewById(R.id.flipper);

        for (int i = 0; i < 10; i++) {
            ImageView img = new ImageView(getContext());
            //img.setImageResource(R.drawable.tea + i);
            flipper.addView(img);
        }

        Animation showIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);      //왼쪽에서 슬라이딩되며 등장

        flipper.setInAnimation(showIn);                                                                   //ViewFlipper에게 등장 애니메이션 적용

        flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);                            //오른쪽으로 슬라이딩되며 퇴장

        toggle_Flipping = (ToggleButton)View.findViewById(R.id.toggle_auto);                              //자동 Flipping 선택 ToggleButton에 따른 작업

        toggle_Flipping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {         //ToggleButton에 Toggle상태 변경 리스너 세팅(OnCheckedChangedListener)

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {                                  // on으로 바뀌어있을 때 자동 Flipping 시작

                    flipper.setFlipInterval(1000);                //1초의 간격으로 ViewFlipper의 View 자동 교체
                    flipper.startFlipping();                      //자동 Flipping 시작

                } else {                                          //OFF로 변경되었으므로  Flipping 정지

                    flipper.stopFlipping();                       //Flipping 정지
                    ;
                }
            }
        });

        Button btn_previous = (Button)View.findViewById(R.id.btn_previous);
        btn_previous.setOnClickListener(
                new ImageButton.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), HomeFragment.class);

                        flipper.showPrevious();                   //이전 View로 교체
                    }
                }

        );

        Button btn_next = (Button)View.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(
                new ImageButton.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), HomeFragment.class);

                        flipper.showNext();                      //다음 View로 교체
                    }
                }

        );

        return View;

    }
}