package practica2;

/**
 * Interfícia de comunicació del patró per esdeveniments
 * <p>
 * No és òptim però és molt visual acadèmicament per entendre el funcionament,
 * emprar un String com entitat de comunicació.
 *
 * @author nadalLlabres
 */
public interface EventsListener {
    void notify(String message);
}
