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
           if (moveEast()) {
               System.out.println("east");
           }
        }
    }

    private boolean moveEast() {
        return mvcEvents.getView().getBoard().moveEast();
    }

    private boolean moveWest() {
        return mvcEvents.getView().getBoard().moveWest();
    }

    private boolean moveNorth() {
        return mvcEvents.getView().getBoard().moveNorth();
    }

    private boolean moveSouth() {
        return mvcEvents.getView().getBoard().moveSouth();
    }
}
