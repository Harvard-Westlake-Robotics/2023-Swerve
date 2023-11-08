package frc.robot.Util;

import java.util.ArrayList;
import java.util.List;

// A class that represents a simplistic promise for asynchronous operations.
public class Promise {

    private boolean resolved = false; // Flag to check if the promise has been resolved.

    // A list to hold the lambda expressions (callbacks) that should run after the promise is resolved.
    private List<Lambda> callbacks = new ArrayList<>();

    // Checks if the promise has been resolved.
    public boolean isResolved() {
        return resolved;
    }

    // Creates and resolves a promise immediately.
    public static Promise immediate() {
        Promise promise = new Promise();
        promise.resolve();
        return promise;
    }

    // Adds a callback to be executed when the promise is resolved.
    public void then(Lambda callback) {
        if (resolved) {
            callback.run();
        } else {
            callbacks.add(callback);
        }
    }

    // Adds a chain of promises, resolving each sequentially.
    public Promise then(Getter<Promise> promiseGetter) {
        if (resolved) {
            return promiseGetter.get();
        } else {
            Container<Promise> nextPromiseContainer = new Container<>(null);
            Promise returnPromise = new Promise();

            // Add a lambda that sets the next promise when this promise is resolved.
            callbacks.add(() -> nextPromiseContainer.val = promiseGetter.get());

            // When this promise is resolved, resolve the next promise.
            this.then(() -> nextPromiseContainer.val.then(returnPromise::resolve));

            return returnPromise;
        }
    }

    // Resolves the promise, running any callbacks that have been added.
    public void resolve() {
        if (!resolved) {
            resolved = true;
            for (Lambda callback : callbacks) {
                callback.run();
            }
        }
    }
}