package wallet.exception;

import javax.persistence.LockTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleInvalidInputException(final Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { WalletException.class })
    public ResponseEntity<Object> handleWalletException(final WalletException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(value = { LockTimeoutException.class })
    public ResponseEntity<Object> handleLockTimeoutException(final LockTimeoutException ex) {
        return new ResponseEntity<>("Sorry, try again", HttpStatus.REQUEST_TIMEOUT);
    }
}
