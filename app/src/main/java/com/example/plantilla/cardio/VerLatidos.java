package com.example.plantilla.cardio;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.plantilla.R;

/**
 * Created by choqu_000 on 09/07/2016.
 * Esta clase extiende la clase View y está diseñado
 * dibujar la imagen latido del corazón.
 */
public class VerLatidos extends View {
    //Atributos
    private static final Matrix matrix = new Matrix();
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static Bitmap greenBitmap = null;
    private static Bitmap redBitmap = null;

    private static int parentWidth = 0;
    private static int parentHeight = 0;


    /**
     * Simple constructor para usar al crear una vista de código.
     *
     * @param context El contexto de la vista se está ejecutando en, a través del cual se puede
     * Acceso al tema actual, los recursos, etc..
     */
    public VerLatidos(Context context) {
        super(context);
        greenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_icon);
        redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_icon);
    }

    /**
     * Constructor que se llama cuando llama una vista desde XML. Se llama
     * Cuando una vista está siendo construido a partir de un archivo XML, el suministro de atributos
     * Que se especifica en el archivo XML. Esta versión utiliza un estilo predeterminado de
     * 0, por lo que los únicos valores de los atributos aplicados son los que están en el tema del Contexto
     * Y la AttributeSet dado.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context El contexto de la vista se está ejecutando en, a través del cual se puede
     * Acceso al tema actual, los recursos, etc.
     * @param Attrs Los atributos de la etiqueta XML que se está inflando la vista.
     * @see //#View(Context, AttributeSet, int)
     */
    public VerLatidos(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Cambio de color cuando indexa el indice
        greenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_icon);
        redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_icon);
    }

    /**
     * Realizar la inflación a partir de XML y aplicar un estilo de base específica de clase a partir de una
     * Atributo de tema. Este constructor de vista permite subclases a utilizar su
     * Propio estilo de base cuando se están inflando. Por ejemplo, una clase de botón
     * Constructor llamaría a esta versión del super constructor de clase y
     * Fuente de <code> R.attr.buttonStyle </ code> de <var> defStyleAttr </ var>; esta
     * Permite estilo del botón del tema para modificar todos los atributos de vista de base
     * (En particular su fondo), así como los atributos de la clase de botón.
     *
     * @param context      El contexto de la vista se está ejecutando en, a través del cual se puede
     *                     Acceso al tema actual, los recursos, etc..
     * @param attrs        Los atributos de la etiqueta XML que se está inflando la vista.
     * @param defStyleAttr Un atributo en el tema actual que contiene una
     *                     Referencia a un recurso de estilo que proporciona
                           valores predeterminados para la vista.
                           Puede ser 0 para no buscar los valores predeterminados.
     * @see //#View(Context, AttributeSet)
     */
    public VerLatidos(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Realizar la inflación a partir de XML y aplicar un estilo de base específica de clase a partir de una
     * Atributo tema o recurso de estilo. Este constructor de vista permite
     * Subclases que utilizan su propio estilo de base cuando se están inflando.
     * <P />
     * Al determinar el valor final de un atributo particular, hay
     * Cuatro entradas que entran en juego:
     * <Ol>
     * <Li> Cualquier valores de atributos en el AttributeSet dado.
     * <Li> El recurso de estilo especificado en el AttributeSet (llamado "estilo").
     * <Li> El estilo por defecto especificado por <var> defStyleAttr </ var>.
     * <Li> El estilo por defecto especificado por <var> defStyleRes </ var>.
     * <Li> Los valores de base en este tema.
     * </ Ol>
     * <P />
     * Cada una de estas entradas se considera en orden, con la primera toma cotizada
     * Precedencia sobre las siguientes. En otras palabras, si en el
     * AttributeSet que ha suministrado <code> & lt; Botón * textColor = "# ff000000" & gt; </ code>
     *, A continuación, el texto del botón <em> siempre </ em> ser de color negro, con independencia de
     * Lo que se especifica en cualquiera de los estilos.
     *
     * @param context      El contexto de la vista se está ejecutando en, a través del cual se puede
     *                     Acceso al tema actual, los recursos, etc.
     * @param attrs        Los atributos de la etiqueta XML que se está inflando la vista.
     * @param defStyleAttr Un atributo en el tema actual que contiene una
     *                     Referencia a un recurso de estilo que proporciona valores predeterminados para
     *                     la vista. Puede ser 0 para no buscar valores predeterminados.
     * @param defStyleRes  Un identificador de recursos de un recurso de estilo que
     *                     Los valores predeterminados para los suministros de la vista, que se utiliza sólo si
     *                     DefStyleAttr es 0 o no se puede encontrar en el tema. Puede ser 0
     *                     Para que no busque por defecto.
     * @see //#View(Context, AttributeSet, int)
     */



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerLatidos(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) throw new NullPointerException();

        Bitmap bitmap = null;
        if (MonitorCardiaco.getCurrent() == MonitorCardiaco.TYPE.GREEN) bitmap = greenBitmap;
        else bitmap = redBitmap;

        int bitmapX = bitmap.getWidth() / 2;
        int bitmapY = bitmap.getHeight() / 2;

        int parentX = parentWidth / 2;
        int parentY = parentHeight / 2;

        int centerX = parentX - bitmapX;
        int centerY = parentY - bitmapY;

        matrix.reset();
        matrix.postTranslate(centerX, centerY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
