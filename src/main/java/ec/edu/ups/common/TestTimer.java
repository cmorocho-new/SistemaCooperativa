package ec.edu.ups.common;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

@Stateless
public class TestTimer {

    /**
     * Default constructor. 
     */
    public TestTimer() {
        // TODO Auto-generated constructor stub
    }
	
	@SuppressWarnings("unused")
	@Schedule(second="*/10", minute="*", hour="8-23", dayOfWeek="Mon-Fri",
      dayOfMonth="*", month="*", year="2020", info="MyTimer")
	private void scheduledTimeout(final Timer t) {
        System.out.println("@Schedule called at EJECUCION...: " + new java.util.Date());
    }
}