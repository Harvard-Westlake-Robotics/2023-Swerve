package frc.robot.Util;

public abstract class Promise {
    public abstract boolean isResolved();

    public abstract void then(Lambda callback);

    public Promise then(Getter<Promise> promiseGetter) {
        if (isResolved()) {
            return promiseGetter.get();
        } else {
            SimplePromise returnPromise = new SimplePromise();

            then(() -> {
                Promise promise = promiseGetter.get();
                promise.then(() -> {
                    returnPromise.resolve();
                });
            });

            return returnPromise;
        }
    }

    public static Promise all(Promise... proms) {
        var resolved = new Container<Integer>(0);
        var all = new SimplePromise();
        for (var prom : proms) {
            prom.then(() -> {
                resolved.val++;
                if (resolved.val == proms.length)
                    all.resolve();
            });
        }
        return all;
    }

    // Creates and resolves a promise immediately.
    public static SimplePromise immediate() {
        SimplePromise promise = new SimplePromise();
        promise.resolve();
        return promise;
    }
}
