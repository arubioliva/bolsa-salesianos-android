package com.example.bolsasalesianos.ui.home.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Credential;
import com.example.bolsasalesianos.pojos.Enterprise;
import com.example.bolsasalesianos.pojos.Status;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnterpriseRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterpriseRegisterFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Database database;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.register_enterprise_button).setOnClickListener(this);
        database = new Database();
    }

    @Override
    public void onClick(View view) {
        if (validForm()) {
            searchEnterpriseByCif();
        }
    }

    /*
    * Comprueba que la empresa a registrar no ha sido registrada anteriormente por su cif.
    * */
    public void searchEnterpriseByCif() {
        Enterprise enterprise = new Enterprise();
        enterprise.setCif(getViewText(R.id.register_cif));
        database.getServices().searchEnterprise(enterprise).enqueue(new Callback<Enterprise>() {
            @Override
            public void onResponse(Call<Enterprise> call, Response<Enterprise> response) {
                Enterprise myResponse = response.body();
                if (myResponse == null) {
                    searchEnterpriseByName();
                } else {
                    setTextViewError(R.id.register_cif, "El cif de esta empresa ya está registrado.");
                }
            }

            @Override
            public void onFailure(Call<Enterprise> call, Throwable t) {

            }
        });
    }

    /*
     * Comprueba que la empresa a registrar no ha sido registrada anteriormente por su nombre.
     * */
    public void searchEnterpriseByName() {
        Enterprise enterprise = new Enterprise();
        enterprise.setName(getViewText(R.id.register_enterprise_name));
        database.getServices().searchEnterprise(enterprise).enqueue(new Callback<Enterprise>() {
            @Override
            public void onResponse(Call<Enterprise> call, Response<Enterprise> response) {
                Enterprise myResponse = response.body();
                if (myResponse == null) {
                    createUser();
                } else {
                    setTextViewError(R.id.register_enterprise_name, "Esta empresa ya está registrada.");
                }

            }

            @Override
            public void onFailure(Call<Enterprise> call, Throwable t) {

            }
        });
    }

    /*
     * Comprueba o que los credenciales no esten repetidos.
     * */
    private void createUser() {
        Credential credential = createCredentialsFromForm();
        database.getServices().newCredential(credential).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if (status.getStatus() == 0) {
                    searchCredentialsAndCreateEnterprise(credential);
                } else {
                    setTextViewError(R.id.register_enterprise_user, "Este nombre de usuario está ocupado.");
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    /*
     * Crea la empresa a partir del formulario de empresa.
     * */
    private void searchCredentialsAndCreateEnterprise(Credential credential) {
        database.getServices().searchCredential(credential).enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, Response<Credential> response) {
                Credential newCredential = response.body();
                if (newCredential.getId() != null) {
                    Enterprise enterprise = createEnterpriseFromForm(newCredential.getId());
                    createEnterprise(enterprise);
                } else {
                    Toast.makeText(getContext(), "Ole no :C1", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Credential> call, Throwable t) {

            }
        });
    }

    /*
     * Introduce la empresa en la base de datos.
     * */
    public void createEnterprise(Enterprise enterprise) {
        database.getServices().newEnterprise(enterprise).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if (status.getStatus() == 0) {

                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });

    }

    /*
     * Genera un objeto de tipo empresa a partir del formulario y el id del credencial.
     * */
    public Enterprise createEnterpriseFromForm(String credential) {
        return new Enterprise(getViewText(R.id.register_cif), getViewText(R.id.register_enterprise_name), getViewText(R.id.register_enterprise_contact_name), getViewText(R.id.register_enterprise_phone), getViewText(R.id.register_enterprise_email), credential);
    }


    /*
    * Comprueba que el formulario de la empresa sea correcto.
    * */
    public boolean validForm() {
        boolean correct = isValid(R.id.register_cif, "^\\d{10}$", "El cif no es válido.(10 digitos)")
                & isValid(R.id.register_enterprise_name, "^((\\w|ñ){2,} *){1,4}$", "El nombre no es válido.")
                & isValid(R.id.register_enterprise_contact_name, "^((\\w|ñ){2,} *){1,4}$", "El nombre no es válido.")
                & isValid(R.id.register_enterprise_phone, "^([+](\\d{1,3}([-]\\d{1,3})?) ?)?\\d{9}$", "El número de teléfono no es valido.")
                & isValid(R.id.register_enterprise_email, "^\\w+@\\w+[.]\\w{2,3}$", "El email no es válido.")
                & isValid(R.id.register_enterprise_user, "^(\\w|ñ){2,}$", "El nombre de usuario no es válido.")
                & isValid(R.id.register_enterprise_pass, "^(?=.*([A-Z]|Ñ))(?=.*([a-z]|ñ))(?=.*[0-9])(?=.*[#?!@$ %^&*-]).{6,}$", "La contraseña debe contener mayusculas, minusculas simbolos y una longitud de 6 o más.");

        if (!getViewText(R.id.register_enterprise_pass).equals(getViewText(R.id.register_enterprise_pass_2))) {
            setTextViewError(R.id.register_enterprise_pass_2, "Las contraseñas no coinciden.");
            correct = false;
        }
        return correct;
    }


    /*
     * Metodo que recibe una regex y un error y en caso de que el campo no coincida con los valores
     * esperados mostrara un error en el textview.
     * */
    public boolean isValid(int id, String regex, String error) {
        if (!Pattern.compile(regex).matcher(getViewText(id)).matches()) {
            setTextViewError(id, error);
            return false;
        }
        return true;
    }

    /*
    * Pone un error a un textview.
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
    * Crea unos credenciales a partir del formulario.
    * */
    public Credential createCredentialsFromForm() {
        return new Credential(getViewText(R.id.register_enterprise_user), getViewText(R.id.register_enterprise_pass), "0000-00-00", "Empresa");
    }

    public EnterpriseRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnterpriseRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterpriseRegisterFragment newInstance(String param1, String param2) {
        EnterpriseRegisterFragment fragment = new EnterpriseRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enterprise_register, container, false);
    }
}