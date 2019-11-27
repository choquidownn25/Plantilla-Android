package com.example.plantilla.webrtc.datos;

/**
 * Created by choqu_000 on 05/06/2016.
 */
public class OrdennoCita {

    //Atributo
    private int tipoOrden;
    public int ingresoOrdenServicios;
    //Constructor
    public OrdennoCita(){

    }

    //Encapsulamiento
    public int getTipoOrden() {
        return tipoOrden;
    }


    public int setTipoOrden(int tipoOrden) {
        this.tipoOrden = tipoOrden;
        ingresoOrdenServicios = tipoOrden;
        getOtengoNumeroOrden(tipoOrden);
        return tipoOrden;
    }

    public int getOtengoNumeroOrden(int tipoOrden){
        return tipoOrden;
    }

    public int obtengoOrde(){
        getOtengoNumeroOrden(tipoOrden);
        return tipoOrden;
    }
}
