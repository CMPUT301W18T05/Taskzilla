package com.cmput301w18t05.taskzilla.request;

/**
 * Created by wyatt on 07/03/18.
 */

/**
 * Request abstract class
 *
 * Abstracts the request object
 */

public abstract class Request {
    private boolean serverRequired;

    public abstract void execute();

    public abstract void executeOffline();

    public boolean requiresConnection() {
        return serverRequired;
    }

}
