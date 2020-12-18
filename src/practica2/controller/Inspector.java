package practica2.controller;

import practica2.MVCEvents;

public class Inspector extends Thread {

    private boolean treasureFound = false;
    private MVCEvents mvcEvents;

    public Inspector(MVCEvents mvcEvents) {
        this.mvcEvents = mvcEvents;
    }

    @Override
    public void run() {
        while (!treasureFound) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveEast();
        }
    }

    private void moveEast() {
        mvcEvents.getView().getBoard().moveEast();
    }
}
