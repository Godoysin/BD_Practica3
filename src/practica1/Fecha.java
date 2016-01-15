package practica1;

import java.util.*;
import java.text.*;

public class Fecha{
    SimpleDateFormat fformat = new SimpleDateFormat("yyyy-MM-dd");
    private int fanio;
    private int fmes;
    private int fdia;
    private Calendar ffecha = new GregorianCalendar();
    
    public Fecha(int anio, int mes, int dia){
        this.fanio = anio;
        this.fmes = mes;
        this.fdia = dia;
    }
    public void GetFecha(){
        ffecha.set(fanio, fmes, fdia);
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
    public String GetFechaasString(){
    	ffecha.set(fanio, fmes, fdia);
    	return fformat.format(ffecha.getTime());
    }
}
