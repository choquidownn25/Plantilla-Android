package com.example.plantilla.ui.utilidades;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

public class Utils {

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Altura y anchura de imagen en bruto
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calcula el más grande de la muestra el valor Tamaño de que es una potencia de 2 y mantiene ambos
            // Altura y la anchura mayor que la altura y el ancho requerido.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // Primero decodificación con en Sólo Decode Límites = true para comprobar las dimensiones
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calcular en Tamaño de la muestra
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Bitmap Decode con en conjunto Tamaño de la muestra
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListenerCompat(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (hasJellyBean()) {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    @SuppressWarnings("deprecation")
    public static void setBackgroundCompat(View v, Drawable drawable) {
        if (hasJellyBean()) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }
    /**
     *  Utiliza las constantes finales estáticas para detectar si versión de la
     *  plataforma del dispositivo es Lollipop o
     *  mas tarde.
     * /
     */

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Utiliza las constantes finales estáticas para detectar si versión de
     * la plataforma del dispositivo es Lollipop o
     * mas tarde.
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
