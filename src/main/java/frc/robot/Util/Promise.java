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
}
