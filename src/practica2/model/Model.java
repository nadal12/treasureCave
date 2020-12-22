package practica2.model;

import practica2.EventsListener;
import practica2.MVCEvents;

/**
 * @author nadalLlabres
 */
public class Model implements EventsListener {

    private MVCEvents mvcEvents;

    /**
     * Constructor
     */
    public Model(MVCEvents mvcEvents) {
        this.mvcEvents = mvcEvents;
    }

    @Override
    public void notify(String message) {
        System.out.println("Mensaje en Modelo: " + message);
    }

}
