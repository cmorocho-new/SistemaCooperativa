package ec.edu.ups.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static int obtenerEdad(Date fechaNacimeitno){
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar bday = new GregorianCalendar();
        GregorianCalendar bdayThisYear = new GregorianCalendar();

        bday.setTime(fechaNacimeitno);
        bdayThisYear.setTime(fechaNacimeitno);
        bdayThisYear.set(Calendar.YEAR, today.get(Calendar.YEAR));

        int age = today.get(Calendar.YEAR) - bday.get(Calendar.YEAR);

        if(today.getTimeInMillis() < bdayThisYear.getTimeInMillis())
            age--;

        return age;
    }

    public static Date sumarDiasFecha(Date fechaOriginal, int dias){
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaOriginal);
        fecha.add(Calendar.DAY_OF_YEAR, dias);
        return fecha.getTime();
    }

    public static Date sumarMesesFecha(Date fechaOriginal, int meses){
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaOriginal);
        fecha.add(Calendar.MONTH, meses);
        return fecha.getTime();
    }
}
