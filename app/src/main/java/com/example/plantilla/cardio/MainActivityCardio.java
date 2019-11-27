package com.example.plantilla.cardio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantilla.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by choqu_000 on 19/07/2016.
 */
public class MainActivityCardio extends Activity {
    //Atributos
    private Timer timer = new Timer();//Tiempo
    private TimerTask task;
    private static int gx;
    private static int j;

    private static double flag = 1;
    private Handler handler;
    private String title = "Pulso";
    private XYSeries series;
    private XYMultipleSeriesDataset mDataset;
    private GraphicalView chart;
    private XYMultipleSeriesRenderer renderer;
    private Context context;
    private int addX = -1;
    double addY;
    int[] xv = new int[300];
    int[] yv = new int[300];
    int[] hua=new int[]{9,10,11,12,13,14,13,12,11,10,9,8,7,6,7,8,9,10,11,10,10};

    private static final AtomicBoolean processing = new AtomicBoolean(false);
    //Android Clase para la redicionamiento de la camara
    private static SurfaceView preview = null;
    //Android control de la superficie
    private static SurfaceHolder previewHolder = null;
    //Android clase de la camara
    private static Camera camera = null;
    //private static View image = null;
    private static TextView mTV_Heart_Rate = null;
    private static TextView mTV_Avg_Pixel_Values = null;
    private static TextView mTV_pulse = null;
    private static WakeLock wakeLock = null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];


    /**
     * Tipo de enumeración
     */
    public static enum TYPE {
        GREEN, RED
    };

    //Establecer el tipo de defecto
    private static TYPE currentType = TYPE.GREEN;
    //Obtener el tipo de corriente
    public static TYPE getCurrent() {
        return currentType;
    }
    //El tamaño de los latidos cardíacos matriz
    private static int beatsIndex = 0;
    //El tamaño de los latidos cardíacos matriz
    private static final int beatsArraySize = 3;
    //matriz del latido del corazón
    private static final int[] beatsArray = new int[beatsArraySize];
    //pulso de latido cardiaco
    private static double beats = 0;
    //Hora de inicio
    private static long startTime = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cardio_grafica);

        initConfig();
    }

    /**
     * Configuración inicial
     */
    @SuppressLint("InvalidWakeLockTag")
    @SuppressWarnings("deprecation")
    private void initConfig() {
        //Curva
        context = getApplicationContext();

        //Aquí interfaz principal para el diseño, la siguiente tabla se basará en el diseño interior
        LinearLayout layout = (LinearLayout)findViewById(R.id.id_linearLayout_graph);

        //Esta clase se utiliza para colocar todos los puntos de la curva,
        // es una colección de puntos, dibujar curvas basado en estos puntos
        series = new XYSeries(title);

        //Crear una instancia de un conjunto de datos,
        // el conjunto de datos se utilizará para crear el gráfico
        mDataset = new XYMultipleSeriesDataset();

        //Agregue el punto de referencia para el conjunto de datos
        mDataset.addSeries(series);

        //La siguiente es una estilos de gráficos y atributos, etc ajustes,
        // procesador para hacer el equivalente de un gráfico utilizado
        // para manejar la representación
        int color = Color.GREEN;
        PointStyle style = PointStyle.CIRCLE;
        renderer = buildRenderer(color, style, true);

        //Establecer estilos de tabla
        setChartSettings(renderer, "X", "Y", 0, 300, 4, 16, Color.WHITE, Color.WHITE);

        //generar gráficos
        chart = ChartFactory.getLineChartView(context, mDataset, renderer);

        //Para añadir iconos a la disposición de
        layout.addView(chart, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        //Aqui Handler Instancia de Timer con los siguientes ejemplos, las características
        // completas gráfico actualizado regularmente
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //Carta de actualización
                updateChart();
                super.handleMessage(msg);
            }
        };

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, 1,20);           //curva
        //obtener controles SurfaceVi
        preview = (SurfaceView) findViewById(R.id.id_preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mTV_Heart_Rate = (TextView) findViewById(R.id.id_tv_heart_rate);
        mTV_Avg_Pixel_Values = (TextView) findViewById(R.id.id_tv_Avg_Pixel_Values);
        mTV_pulse = (TextView) findViewById(R.id.id_tv_pulse);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
    }

    //	curva
    @Override
    public void onDestroy() {
        //Cuando el programa termina apagado Timer
        timer.cancel();
        super.onDestroy();
    };

    /**
     * crear un gráfico
     */
    protected XYMultipleSeriesRenderer buildRenderer(int color, PointStyle style, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        //La curva sí los estilos de gráficos, incluyendo el color,
        // tamaño de punto y el grosor de línea, etc.
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.RED);
        r.setLineWidth(1);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    /**
     * Estilo icono de configuración
     * @param renderer
     * @param xTitle：eje xTitulo
     * @param yTitle：eje yTitulo
     * @param xMin：numero xMinimo
     * @param xMax：numero xMaximo
     * @param yMin:numero yMinimo
     * @param yMax：numero yMaximo
     * @param axesColor：Color
     * @param labelsColor：etiqueta
     */
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String xTitle, String yTitle,
                                    double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        //Sobre el procesamiento de la carta se pueden encontrar en la documentación de la API
        renderer.setChartTitle(title);
        renderer.setChartTitleTextSize(20);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GREEN);
        renderer.setXLabels(40);
        renderer.setYLabels(20);
        renderer.setXTitle("Tiempo");
        renderer.setYTitle("mmHg");
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setPointSize((float) 3 );
        renderer.setShowLegend(false);
    }

    /**
     * Icono de la información de actualización
     */
    private void updateChart() {
        //El siguiente conjunto de nodos necesitan aumentar
        if(flag == 1) {
            addY = 10;
        }
        else {
            flag = 1;
            if(gx < 200){
                if(hua[20] > 1){
                    Toast.makeText(MainActivityCardio.this, "Porfavor, use un dedo para cubrir el lente de la cámara y el flash al mismo tiempo！", Toast.LENGTH_SHORT).show();
                    hua[20] = 0;
                }
                hua[20]++;
                return;
            }
            else {
                hua[20] = 10;
            }
            j = 0;
        }
        if(j < 20){
            addY=hua[j];
            j++;
        }

        //Retire el punto de recogida de datos centralizada
        mDataset.removeSeries(series);

        //Analizando el punto de enfoque actual al final el número de puntos,
        // ya que la pantalla sólo tiene capacidad para un total de 100,
        // por lo que cuando hay más de 100 puntos, la longitud es siempre 100
        int length = series.getItemCount();
        int bz = 0;
        //addX = length;
        if (length > 300) {
            length = 300;
            bz=1;
        }
        addX = length;
        //Cuando los valores anteriores de X e Y se toma
        // hacia fuera en el punto de copia de seguridad, y el valor de x más 1,
        // haciendo que la curva en el sentido de derecha panning
        for (int i = 0; i < length; i++) {
            xv[i] = (int) series.getX(i) - bz;
            yv[i] = (int) series.getY(i);
        }

        //Punto establece en vacío, con el fin de hacer un nuevo conjunto de puntos y preparación
        series.clear();
        mDataset.addSeries(series);
        //El primer punto de la nueva generación de punto de enfoque agregado,
        // y luego una serie de puntos en el cuerpo del bucle de coordenadas se
        // convierten a reunirse con el punto de enfoque
        //Aquí se puede experimentar con el orden invertido qué efecto, que es ejecutar el
        // cuerpo del bucle, y luego añadir una nueva generación de punto
        series.add(addX, addY);
        for (int k = 0; k < length; k++) {
            series.add(xv[k], yv[k]);
        }
        //En el conjunto de datos para agregar un nuevo conjunto de puntos
        //mDataset.addSeries(series);

        //Ver actualizar, sin este paso, la curva no rinden dinámico
        //Si el hilo principal no interfaz de usuario, llame postInvalidate (), con especial referencia aapi
        chart.invalidate();
    } //curva


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        camera = Camera.open();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }


    /**
     * Método previa de la cámara Características de interfaz de la interfaz de usuario
     * Este método actualiza de forma dinámica,
     * Obtener parámetro por la cámara del teléfono móvil en tiempo
     * real para calcular dinámicamente el valor medio de píxeles, el número de impulsos de modo
     * que el cálculo dinámico en tiempo real de la frecuencia cardíaca。
     */
    private static PreviewCallback previewCallback = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null) {
                throw new NullPointerException();
            }
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null) {
                throw new NullPointerException();
            }
            if (!processing.compareAndSet(false, true)) {
                return;
            }
            int width = size.width;
            int height = size.height;

            //Procesamiento de imágenes
            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(),height,width);
            gx = imgAvg;
            //Valor para mostrar el promedio
            //mTV_Avg_Pixel_Values.setText("Valor Promedio = " + String.valueOf(imgAvg));

            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }
            //el cálculo de la media
            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            //El cálculo de la media
            int rollingAverage = (averageArrayCnt > 0)?(averageArrayAvg/averageArrayCnt):0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    flag=0;
                    //Texto para Mosrar los pulsos
                    //mTV_pulse.setText("Número de impulsos = " + String.valueOf(beats));
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if(averageIndex == averageArraySize) {
                averageIndex = 0;
            }
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            if (newType != currentType) {
                currentType = newType;
            }

            //Obtener Sistema Hora de finalización（ms）
            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 2) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180|| imgAvg < 200) {
                    //Obtener el tiempo de inicio del sistema（ms）
                    startTime = System.currentTimeMillis();
                    //beats latido cardiaco total
                    beats = 0;
                    processing.set(false);
                    return;
                }

                if(beatsIndex == beatsArraySize) {
                    beatsIndex = 0;
                }
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
                /*
                mTV_Heart_Rate.setText("Su ritmo cardíaco: "+String.valueOf(beatsAvg) +
                        "  Valor:" + String.valueOf(beatsArray.length) +
                        "    " + String.valueOf(beatsIndex) +
                        "    " + String.valueOf(beatsArrayAvg) +
                        "    " + String.valueOf(beatsArrayCnt)

                ); */


                mTV_Heart_Rate.setText("Su ritmo cardíaco es: "+String.valueOf(beatsAvg));



                //Obtener la hora del sistema（ms）
                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }
    };

    /**
     *  Interfaz de devolución de llamada de previsualización
     */
    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        //Cuando se crea una llamada
        @SuppressLint("LongLogTag")
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCallback","Exception in setPreviewDisplay()", t);
            }
        }

        //Al previsualizar los cambios cuando este método de devolución de llamada
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        //Al llamar destruida
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    /**
     * Obtener una vista previa de la cámara tamaño más pequeño
     */
    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                }
                else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea) {
                        result = size;
                    }
                }
            }
        }
        return result;
    }



}

