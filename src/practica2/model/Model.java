package practica2.model;

import practica2.EventsListener;
import practica2.MVCEvents;

/*
  Esta clase ahora mismo no tiene ningún tipo de funcionalidad porque los datos del programa
  únicamente son piezas y sus movimientos asociados. Se ha decidido crear una clase para cada
  una de ellas para tener una mejor gestión y estructura (también por requisito del enunciado
  de la práctica). Sin embargo se mantiene dicha clase por si en el futuro se añaden más
  funcionalidades que requieran más datos y así se podría hacer el tratamiento aqui mismo.
  También se mantiene por completitud del MVC.
 */

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
