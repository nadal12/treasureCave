package practica2;

import practica2.controller.Controller;
import practica2.model.Model;
import practica2.view.View;

public class MVCEvents {

    private Model model;                // Puntero al Modelo
    private View view;                  // Puntero a la Vista
    private Controller controller;      // Puntero al Controlador

    /**
     * Construcció de l'esquema MVC
     */
    private void init() {
        model = new Model(this);
        controller = new Controller(this);
        view = new View("Práctica 2 - La cueva del tesoro", this);
        view.start();
    }

    public static void main(String[] args) {
        (new MVCEvents()).init();
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }

    public Controller getController() {
        return controller;
    }
}
