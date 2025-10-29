package challenge.devices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DeviceValidationException extends RuntimeException {
    public DeviceValidationException(String message) {
        super(message);
    }
}
