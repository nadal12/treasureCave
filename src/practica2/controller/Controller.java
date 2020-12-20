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
    }

    @Override
    public void notify(String message) {
        System.out.println("Mensaje en Control: " + message);

        if (message.startsWith("Start")) {
            inspector = new Inspector(mvcEvents);
            inspector.start();
        } else if (message.startsWith("Stop")) {
            inspector.stopInspector();
        } else if (message.startsWith("Delay")) {
            inspector.setDelay(Integer.parseInt(message.split(",")[1]));
        }
    }
}
