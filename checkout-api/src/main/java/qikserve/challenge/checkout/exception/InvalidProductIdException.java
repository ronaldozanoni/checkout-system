package qikserve.challenge.checkout.exception;

public class InvalidProductIdException extends RuntimeException {

    public InvalidProductIdException(String message) {
        super(message);
    }
}
