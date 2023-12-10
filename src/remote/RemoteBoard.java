package remote;

import environment.Board;

/**
 * Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Server.
 * Only for part II of the project.
 *
 * @author luismota
 */
public class RemoteBoard extends Board {

    @Override
    public int getLastKeyPressed() {
        return lastKeyPressed;
    }

    @Override
    public void setLastKeyPressed(int key) {
        lastKeyPressed = key;
    }

    @Override
    public void handleKeyPress(int keyCode) {
        System.out.println("pressed " + keyCode); // remove
        lastKeyPressed = keyCode;
    }

    @Override
    public void handleKeyRelease() {
        // TODO
    }

    @Override
    public void init() {
        System.out.println("RemoteBoard init: " + this);
    }

    @Override
    public void stopSnakes() {
    }
}
