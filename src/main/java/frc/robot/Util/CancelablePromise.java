package frc.robot.Util;

/**
 * The CancelablePromise class extends the Promise class to provide a mechanism for canceling an ongoing asynchronous operation.
 */
public class CancelablePromise extends Promise {
    // A Lambda function that defines the cancel action to be performed if the promise is canceled.
    Lambda cancel;

    /**
     * Constructs a CancelablePromise with a specified action to execute upon cancellation.
     * 
     * @param cancel A Lambda expression that will be executed when the cancel method is called.
     */
    public CancelablePromise(Lambda cancel) {
        this.cancel = cancel;
    }

    /**
     * Cancels the promise by executing the provided Lambda cancel action.
     */
    public void cancel() {
        // Perform the cancellation action, if one was provided.
        if (cancel != null) {
            cancel.run();
        }
    }
}
