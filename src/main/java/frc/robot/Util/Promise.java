package frc.robot.Util;

import java.util.ArrayList;

public class Promise {
    private boolean resolved = false;
    public boolean isResolved() {
        return resolved;
    }
    ArrayList<Lambda> thens = new ArrayList<Lambda>();

    public static Promise immediate() {
        final var prom = new Promise();
        prom.resolve();
        return prom;
    }

    public void then(Lambda then) {
        if (resolved)
            then.run();
        else
            thens.add(then);
    }

    public Promise then(Getter<Promise> then) {
        if (resolved) {
            return then.get();
        } else {
            Container<Promise> nextProm = new Container<Promise>(null);
            Promise retProm = new Promise();

            thens.add(() -> {
                nextProm.val = then.get();
            });

            this.then(() -> {
                nextProm.val.then(() -> {
                    retProm.resolve();
                });
            });

            return retProm;
        }
    }

    public void resolve() {
        if (!resolved) {
            resolved = true;
            for (var exec : thens) {
                exec.run();
            }
        }
    }
}
