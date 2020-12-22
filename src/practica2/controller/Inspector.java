package practica2.controller;

import practica2.MVCEvents;
import practica2.model.figures.Agent;
import practica2.model.figures.Stone;

import java.util.Stack;

public class Inspector extends Thread {

    private static final int EAST = 0;
    private static final int SOUTH = 1;
    private static final int WEST = 2;
    private static final int NORTH = 3;

    private int delay = 300;
    private boolean treasureFound = false;

    private boolean stench = false;
    private boolean breeze = false;
    private boolean knock = false;

    private int actualDirection = EAST;

    private Stack<Coordinate> movements = new Stack<>();

    private MVCEvents mvcEvents;
    private boolean stop = false;

    public Inspector(MVCEvents mvcEvents) {
        this.mvcEvents = mvcEvents;
    }

    @Override
    public void run() {
        while (!treasureFound && !stop) {
            updatePerceptions(getAgentCoordinates());

            //Si no hay nada, avanza en la dirección actual.
            if (!breeze && !stench && !knock) {
                move();
                treasureFound = checkTreasure();
                sleep();
            } else if (knock && !breeze && !stench) {
                //Averiguar a que pared ha golpeado.
                switch (actualDirection) {
                    case EAST, NORTH, SOUTH -> {
                        duplicateCode();
                    }
                    case WEST -> {
                        nextDirection();
                        nextDirection();
                        duplicateCode();
                        nextDirection();
                        nextDirection();
                        sleep();
                    }
                }
                knock = false;
            } else {
                moveOpposite();
                sleep();
                nextDirection();
            }
        }

        if (!stop) {
            dismountPath();
            treasureFoundMessage();
        }
    }

    private void killMonster() {
        if (mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].isMonster()) {
            mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setMonster(false);
            mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setStone(true);
            mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].setPiece(new Stone());
        }
        move();
    }

    private void duplicateCode() {
        nextDirection();
        if (!breeze && !stench) {
            move();
            sleep();
        }
        updatePerceptions(getAgentCoordinates());

        if (breeze || stench) {
            moveOpposite();
            sleep();
        }

        if (actualIsVisited()) {
            for (int i = 0; i < Math.random() * 5; i++) {
                nextDirection();
            }
        }
        nextDirection();
    }

    private void treasureFoundMessage() {
        mvcEvents.getView().notify("found");
    }

    private void dismountPath() {
        while (!movements.empty()) {
            Coordinate coordinate = movements.pop();
            moveTo(coordinate.getRow(), coordinate.getCol());
            sleep();
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
            case NORTH -> moveSouth();
        }
    }

    private void updatePerceptions(int[] agentCoordinates) {
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

    private int[] getAgentCoordinates() {
        return mvcEvents.getView().getBoard().getAgentCell();
    }

    private boolean actualIsVisited() {
        return mvcEvents.getView().getBoard().getCells()[getAgentCoordinates()[0]][getAgentCoordinates()[1]].isVisited();
    }

    public void stopInspector() {
        this.stop = true;
    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
