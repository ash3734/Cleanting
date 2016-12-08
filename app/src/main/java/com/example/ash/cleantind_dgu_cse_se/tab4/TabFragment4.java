package com.example.ash.cleantind_dgu_cse_se.tab4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.LoginActivity;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.SplashActivity;
import com.example.ash.cleantind_dgu_cse_se.User;

/**
 * Created by ash on 2016-11-07.
 */
public class TabFragment4 extends Fragment {

    private Button changeInfoBtn;
    private Button updateMyPostBtn;
    private Button withdrawlBtn;
    private Button logoutBtn;
    private Button informationBtn;



    @Override
    public void onStart() {
        super.onStart();
        changeInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UpdateProfileActivity.class);
                startActivity(intent);

            }
        });
        updateMyPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SplashActivity.class);
                startActivity(intent);
            }
        });
        withdrawlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                withdrawl();
                User.clearID();
                Toast.makeText(getActivity(),"회원탈퇴 하였습니다 adios~",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.clearID();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        informationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"아직 프로토타입입니다.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void withdrawl(){
        String myId=User.ID;
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",myId);
        dbManager.db_connect(User.baseURL+"Withdrawl.php");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_4, container, false);
        changeInfoBtn=(Button)view.findViewById(R.id.change_info_Btn);
        updateMyPostBtn=(Button)view.findViewById(R.id.update_mypost_Btn);
        logoutBtn=(Button)view.findViewById(R.id.logout_Btn);
        withdrawlBtn=(Button)view.findViewById(R.id.withdrawl_Btn);
        updateMyPostBtn=(Button)view.findViewById(R.id.update_mypost_Btn);
        informationBtn=(Button)view.findViewById(R.id.information_Btn);
        return view;
    }
}