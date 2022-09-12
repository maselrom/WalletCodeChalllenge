package wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class WalletException extends HttpServerErrorException {
    public WalletException(HttpStatus statusCode, String message) {
        super(statusCode, message);
    }
}
