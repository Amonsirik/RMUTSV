package app.sirikhamhom.amon.rmutsvservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import app.sirikhamhom.amon.rmutsvservice.R;
import app.sirikhamhom.amon.rmutsvservice.fragment.RegisterFragment;
import app.sirikhamhom.amon.rmutsvservice.utility.GetAllData;
import app.sirikhamhom.amon.rmutsvservice.utility.MyAlert;
import app.sirikhamhom.amon.rmutsvservice.utility.Myconstant;

/**
 * Created by masterung on 6/11/2017 AD.
 */

public class MainFragment extends Fragment{

    private String userString, passwordString;
    private boolean userABoolean = true; // true ==> User False

    //    Manager Worked after onCreateView Success
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();


    }   // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEdiText = getView().findViewById(R.id.edtPassword);

                userString = userEditText.getText().toString().trim();
                passwordString = passwordEdiText.getText().toString().trim();

                if (userString.equals("") || passwordString.equals("")) {
//                    Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Have Space", "Please Fill All Blank");
                }else {
                    checkUserAnPass();
                }

            } // onClick
    });
    }

    private void checkUserAnPass() {
        try {

            Myconstant myconstant = new Myconstant();
            String tag = "8novV1";
            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myconstant.getUrlGetAllUser());
            String strJOSN = getAllData.get();
            Log.d(tag, "JSON ==> " + strJOSN);
            String[] strings = new String[]{"id", "Name", "Category", "User", "Password"};
            String[] userStrings1 = new String[strings.length];

            JSONArray jsonArray = new JSONArray(strJOSN);
            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {
                    userABoolean = false;


                    for (int i1=0; i1<strings.length; i1+=1){
                        userStrings1[i1] = jsonObject.getString(strings[i1]);


                    }

                }

            } // for

            if (userABoolean) {
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("User False" , "No this User in my Database");
            } else if (passwordString.equals(userStrings1[4])) {
                Toast.makeText(getActivity(),"Welcome" + userStrings1[1],
                        Toast.LENGTH_SHORT).show();
            } else {
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("Password False",
                        "Please Try Again Password False");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtregister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace Fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentFragmentMain, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();


            } // onClick
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}   // Main Class