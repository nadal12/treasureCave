package practica2.controller;

import practica2.MVCEvents;

public class Inspector extends Thread {

    private static int SPEED = 700;
    private boolean treasureFound = false;

    private static boolean STENCH = false;
    private static boolean BREEZE = false;
    private static boolean RADIANCE = false;
    private static boolean KNOCK = false;

    private MVCEvents mvcEvents;

    public Inspector(MVCEvents mvcEvents) {
        this.mvcEvents = mvcEvents;
    }

    @Override
    public void run() {
        while (!treasureFound) {
            updatePerceptions(getAgentCoordinates());

            if (BREEZE || STENCH) {
                moveSouth();
            } else {
                moveEast();
            }
            sleep();
        }
    }

    private void updatePerceptions(int [] agentCoordinates) {
        int row = agentCoordinates[0];
        int col = agentCoordinates[1];

        //Percepción 0: Hedor.
        STENCH = mvcEvents.getView().getBoard().getCells()[row][col].isStench();

        //Percepción 1: Brisa.
        BREEZE = mvcEvents.getView().getBoard().getCells()[row][col].isBreeze();

        //Percepción 2: Resplandor.
        //perceptions[0] = mvcEvents.getView().getBoard().getCells()[row][col].isRadiance();

    }

    private boolean moveEast() {
        mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setVisited(true);
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

    private int [] getAgentCoordinates() {
        return mvcEvents.getView().getBoard().getAgentCell();
    }

    private void sleep() {
        try {
            Thread.sleep(SPEED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
