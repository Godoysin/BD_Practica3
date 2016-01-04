package practica1;

import java.util.*;
import java.text.*;

public class Fecha{
    SimpleDateFormat fformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int fanio;
    private int fmes;
    private int fdia;
    private int fhora;
    private int fminuto;
    private int fsegundo = 00;
    private Calendar ffecha = new GregorianCalendar();
    
    public Fecha(int anio, int mes, int dia, int hora, int minuto){
        this.fanio = anio;
        this.fmes = mes;
        this.fdia = dia;
        this.fhora = hora;
        this.fminuto = minuto;
    }
    public void GetFecha(){
        ffecha.set(fanio, fmes, fdia, fhora, fminuto);
        System.out.println(fformat.format(ffecha.getTime()));
    }
    public int GetFechaAnio(){
    	return fanio;
    }
    public int GetFechaMes(){
    	return fmes;
    }
    public int GetFechaDia(){
    	return fdia;
    }
    public int GetFechaHora(){
    	return fhora;
    }
    public int GetFechaMinuto(){
    	return fminuto;
    }
    public String GetFechaasString(){
    	ffecha.set(fanio, fmes, fdia, fhora, fminuto, fsegundo);
    	return fformat.format(ffecha.getTime());
    }
}
