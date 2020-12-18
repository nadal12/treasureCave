package practica2.controller;

import practica2.EventsListener;
import practica2.MVCEvents;

/**
 * @author nadalLlabres
 */
public class Controller implements EventsListener {

    private MVCEvents mvcEvents;
    private Inspector inspector;

    public Controller(MVCEvents mvcEvents) {
        this.mvcEvents = mvcEvents;
        inspector = new Inspector(mvcEvents);
    }

    @Override
    public void notify(String message) {
        System.out.println("Mensaje en Control: " + message);

        if (message.startsWith("Start")) {
            inspector.start();
        } else if (message.startsWith("Stop")) {

        }
    }

}
