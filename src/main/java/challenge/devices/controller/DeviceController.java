package challenge.devices.controller;

import challenge.devices.dto.DeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public DeviceResponse createDevice(@RequestBody @Valid DeviceRequest deviceRequest) {
        log.debug("createDevice(request={})", deviceRequest);
        return deviceService.createDevice(deviceRequest);
    }

}
