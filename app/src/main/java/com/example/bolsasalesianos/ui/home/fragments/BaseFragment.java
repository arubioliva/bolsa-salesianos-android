package com.example.bolsasalesianos.ui.home.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class BaseFragment extends Fragment {
    private SharedPreferences loginSP;
    private SharedPreferences studentSP;

    /*
    * Inicializa los sharedpreferences del estudiante y el login.
    * */
    public void initializeSharedPreferences() {
        setLoginSP(getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE));
        setStudentSP(getActivity().getSharedPreferences("Student", Context.MODE_PRIVATE));
    }

    /*
     * Devuelve los valores de un checkbox 0 para false y 1 para true.
     * */
    public String getCheckValue(int id) {
        return ((CheckBox) getView().findViewById(id)).isChecked() ? "1" : "0";
    }

    /*
     * Devuelve el dia formateado.
     * */
    public String getDay(){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    /*
     * Devuelve los valores de un checkbox 0 para false y 1 para true.
     * */
    public boolean isValid(int id, String regex, String error) {
        if (!Pattern.compile(regex).matcher(getViewText(id)).matches()) {
            setTextViewError(id, error);
            return false;
        }
        return true;
    }

    /*
     * Pone el error a un textview.
     * */
    public void setTextViewError(int id, String error) {
        ((TextView) getView().findViewById(id)).setError(error);
    }

    /*
     * Recoge el valor de un textview.
     * */
    public String getViewText(int id) {
        return ((TextView) getView().findViewById(id)).getText().toString();
    }

    /*
     * Cambia el valor de un textview.
     * */
    public void setViewText(int id, String text) {
        ((TextView) getView().findViewById(id)).setText(text);
    }

    /*
    * Cambia el valor de un checkbox.
    * */
    public void setCheckBox(int id, String value) {
        ((CheckBox) getView().findViewById(id)).setChecked(value.equals("1"));
    }

    public SharedPreferences getLoginSP() {
        return loginSP;
    }

    public void setLoginSP(SharedPreferences loginSP) {
        this.loginSP = loginSP;
    }

    public SharedPreferences getStudentSP() {
        return studentSP;
    }

    public void setStudentSP(SharedPreferences studentSP) {
        this.studentSP = studentSP;
    }

}
