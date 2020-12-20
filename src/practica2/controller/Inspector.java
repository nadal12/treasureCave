package practica2.controller;

import practica2.MVCEvents;
import practica2.model.pieces.Agent;

import java.util.Stack;

public class Inspector extends Thread {

    private static final int EAST = 0;
    private static final int SOUTH = 1;
    private static final int WEST = 2;
    private static final int NORTH = 3;

    private static final int SPEED = 600;
    private static final int RETURN_SPEED = 300;
    private boolean treasureFound = false;

    private boolean stench = false;
    private boolean breeze = false;
    private boolean radiance = false;
    private boolean knock = false;

    private int actualDirection = EAST;

    private Stack<Coordinate> movements = new Stack<>();

    private MVCEvents mvcEvents;

    public Inspector(MVCEvents mvcEvents) {
        this.mvcEvents = mvcEvents;
    }

    @Override
    public void run() {
        while (!treasureFound) {
            updatePerceptions(getAgentCoordinates());

            //Si no hay nada, avanza en la dirección actual.
            if (!breeze && !stench && !knock) {
                move();
                treasureFound = checkTreasure();
                sleep(SPEED);
            } else if (knock) {
                //Averiguar a que pared ha golpeado.
                switch (actualDirection) {
                    case EAST -> {
                        nextDirection();
                        move();
                        nextDirection();
                        sleep(SPEED);
                    }
                    case WEST -> {
                        nextDirection();
                        nextDirection();
                        nextDirection();
                        move();
                        nextDirection();
                        nextDirection();
                        nextDirection();
                        sleep(SPEED);
                    }
                }
                knock = false;
            }
        }

        dismountPath();
        treasureFoundMessage();
    }

    private void treasureFoundMessage() {
        mvcEvents.getView().notify("found");
    }

    private void dismountPath() {
        while (!movements.empty()) {
            Coordinate coordinate = movements.pop();
            moveTo(coordinate.getRow(), coordinate.getCol());
            sleep(RETURN_SPEED);
        }
    }

    private boolean checkTreasure() {
        return mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].isTreasure();
    }

    private void previousDirection() {
        actualDirection = (actualDirection - 1) % 4;
    }

    private void nextDirection() {
        actualDirection = (actualDirection + 1) % 4;
    }

    private void moveOpposite() {
        switch (actualDirection) {
            case EAST -> moveWest();
            case SOUTH -> moveNorth();
            case WEST -> moveEast();
            case NORTH -> moveSouth();        }
    }

    private void updatePerceptions(int [] agentCoordinates) {
        int row = agentCoordinates[0];
        int col = agentCoordinates[1];

        //Percepción 0: Hedor.
        stench = mvcEvents.getView().getBoard().getCells()[row][col].isStench();

        //Percepción 1: Brisa.
        breeze = mvcEvents.getView().getBoard().getCells()[row][col].isBreeze();
    }

    private void moveTo(int row, int col) {
        mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setAgent(false);
        mvcEvents.getView().getBoard().getCells()[row][col].setAgent(true);
        mvcEvents.getView().getBoard().getCells()[row][col].setPiece(new Agent());
    }

    private void move() {
        switch (actualDirection) {
            case EAST -> knock = !moveEast();
            case SOUTH -> knock = !moveSouth();
            case WEST -> knock = !moveWest();
            case NORTH -> knock = !moveNorth();
        }
    }

    private boolean moveEast() {
        movements.push(new Coordinate(getAgentCoordinates()[0], getAgentCoordinates()[1]));
        mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setVisited(true);
        return mvcEvents.getView().getBoard().moveEast();
    }

    private boolean moveWest() {
        movements.push(new Coordinate(getAgentCoordinates()[0], getAgentCoordinates()[1]));
        mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setVisited(true);
        return mvcEvents.getView().getBoard().moveWest();
    }

    private boolean moveNorth() {
        movements.push(new Coordinate(getAgentCoordinates()[0], getAgentCoordinates()[1]));
        mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setVisited(true);
        return mvcEvents.getView().getBoard().moveNorth();
    }

    private boolean moveSouth() {
        movements.push(new Coordinate(getAgentCoordinates()[0], getAgentCoordinates()[1]));
        mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setVisited(true);
        return mvcEvents.getView().getBoard().moveSouth();
    }

    private int [] getAgentCoordinates() {
        return mvcEvents.getView().getBoard().getAgentCell();
    }

    private void sleep(int speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
