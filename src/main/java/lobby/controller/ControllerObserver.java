package lobby.controller;

import lobby.actions.client.ClientAction;

/**
 * Interface of the lobby controller, defines the only method a client can call.
 */
public interface ControllerObserver {

    /**
     * Receives the player action, handles it or returns an exception.
     * This is the only entry point method that receives user inputs.
     *
     * @throws IllegalAccessException if a client tries to make an illegal action.
     */
    void update(ClientAction clientAction) throws IllegalAccessException;
}
