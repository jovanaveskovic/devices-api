package challenge.devices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DeviceInUseException extends RuntimeException {
    public DeviceInUseException(String message) {
        super(message);
    }
}
