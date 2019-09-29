package com.example.plantilla.Orden;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.plantilla.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityOrden extends AppCompatActivity {

    private TextView nombreCliente;
    private EditText direccionDomicilio;
    private EditText descripcion;
    private TextView nombreServicio;
    private View mProgressView;

    private ImageView mLogoView;
    private View mLoginFormView;
    public StringBuilder mensaje = new StringBuilder();
    public StringBuilder numeroZipCode = new StringBuilder();
    public StringBuilder Dirreccion = new StringBuilder();
    public StringBuilder NombreUsuario = new StringBuilder();

    private String idServicio;
    private String idCliente;
    private String direccionSN;
    private String numeroZicode;
    private String Nombremsm;
    private String userName;
    private String Address;
    private String numZipcode;
    private String Nombre;
    public String datoentyti=null;
    private String datoDireccion;
    private String datoIngresaRetrofitDirecion;
    private String DireccionaPasar;
    private EditText date;
    private EditText hora;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String datoFechaOrden;
    private String DatoFechaHoy;
    private TextView fechahoy;
    private String datoCodigoTipoServicio;
    private String datoCodigoServicio;
    private String datoDescripcion;
    private int ingresaDatoCodigoServicio;
    private int ingresoDatoCodigoTipoServicio;
    private String datoNombreClientePasa;
    private String datoDireccionClientePasa;
    private Button formularioCondireccion;

    private PopupWindow popupWindow;
    private TextView idUltimoNumero;
    private TextView nombreclientehoy;
    private TextView txtnombrecliente;
    private TextView txtdireccioncliente;
    private TextView Name;
    private String datoUltimoRegistro;
    private String datoError;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;


    private int datoIdServicio;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView escojerServicio;
    private Menu collapsedMenu;
    private boolean appBarExpanded = true;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private RadioButton r1,r2;
    private String datoUrgencia;
    private RadioButton radioDeposito;
    private RadioGroup grupo;
    private String datoUrgente;
    private TextView textViewRadioButtomUrgencia;
    private TextView textViewTipoOrdenservicio;
    private TextView textViewFechaOrden;
    private TextView textViewHora;
    MaterialSpinner spinners;
    private RadioGroup radiogroup;
    private ImageView imagen;
    private Button botonCargar;
    private Button bntFormularioCondireccion;
    private static final String[] horaVisitaServicio = {
            "General",
            "Preferencial",
            "Vip"
    };
    private String datoHora;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Castinting objeto
        nombreCliente = (TextView)findViewById(R.id.textNombreUsuario);
        direccionDomicilio = (EditText)findViewById(R.id.edittextviewDireccion);
        descripcion = (EditText)findViewById(R.id.edittextviewDescripcion);
        fechahoy = (TextView)findViewById(R.id.txtfechahoy);
        nombreServicio = (TextView)findViewById(R.id.textservicio);
        escojerServicio = (TextView)findViewById(R.id.edittextviewEscojerServicio);
        textViewRadioButtomUrgencia = (TextView)findViewById(R.id.textViewRadioButtom);
        textViewFechaOrden = (TextView)findViewById(R.id.textViewFechaOrden);
        textViewTipoOrdenservicio = (TextView)findViewById(R.id.textViewTipoOrdenservicio);
        textViewHora = (TextView)findViewById(R.id.textViewHora);
        spinners = (MaterialSpinner) findViewById(R.id.spinner);
        radiogroup=(RadioGroup)findViewById(R.id.rdbGp1);

        imagen= (ImageView) findViewById(R.id.imagemId); //Imagen

        // inicializa el date picker
        date = (EditText) findViewById(R.id.date);
        hora = (EditText) findViewById(R.id.hora);
        //date.setVisibility(View.GONE);
        // Evento click
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ActivityOrden.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // establecer el valor del día del mes, mes y año en el texto de edición
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ActivityOrden.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hora.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();

            }
        });


        DatoFechaHoy = getDateTime();
        MaterialSpinner spinners = (MaterialSpinner) findViewById(R.id.spinner);
        spinners.setItems(horaVisitaServicio);
        spinners.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Hora Selecionada " + item, Snackbar.LENGTH_LONG).show();
                datoHora = item; //se le asigna la hora a la orden
            }
        });
        spinners.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });

        bntFormularioCondireccion = (Button) findViewById(R.id.bntFormularioCondireccion);
        bntFormularioCondireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopup();
            }
        });
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );

    }

    //Metodo para la fecha de hoy
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Sale mensaje de Acetado
    private void callPopup() {


        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.custom_layout, null);

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        ((Button) popupView.findViewById(R.id.btnok))
                .setOnClickListener(new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                    public void onClick(View arg0) {

                        //date.setText("");

                        popupWindow.dismiss();

                    }

                });




    }

}
