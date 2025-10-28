package challenge.devices.controller;

import challenge.devices.dto.CreateDeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.dto.UpdateDeviceRequest;
import challenge.devices.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "Creates a new device")
    @PostMapping
    public DeviceResponse createDevice(@RequestBody @Valid CreateDeviceRequest createDeviceRequest) {
        log.debug("createDevice(request={})", createDeviceRequest);
        return deviceService.createDevice(createDeviceRequest);
    }

    @Operation(summary = "Fully updates an existing device")
    @PutMapping(path = "/{deviceId}")
    public DeviceResponse updateDevice(@PathVariable Long deviceId, @RequestBody @Valid UpdateDeviceRequest request) {
        log.debug("updateDevice(deviceId={},request={})", deviceId, request);
        return deviceService.updateDevice(deviceId, request);
    }

    @Operation(summary = "Partially updates an existing device")
    @PatchMapping(path = "/{deviceId}")
    public DeviceResponse partialUpdateDevice(@PathVariable Long deviceId, @RequestBody @Valid UpdateDeviceRequest request) {
        log.debug("partialUpdateDevice(deviceId={},request={})", deviceId, request);
        return deviceService.updateDevice(deviceId, request);
    }

    @Operation(summary = "Fetches single device")
    @GetMapping(path = "/{deviceId}")
    public DeviceResponse updateDevice(@PathVariable Long deviceId) {
        log.debug("getDevice(deviceId={})", deviceId);
        return deviceService.getDevice(deviceId);
    }

    @Operation(summary = "Fetches all devices")
    @GetMapping
    public List<DeviceResponse> getAllDevices() {
        log.debug("getAllDevices()");
        return deviceService.getAllDevices();
    }

    @Operation(summary = "Fetches devices by brand")
    @GetMapping(path = "/{brand}")
    public List<DeviceResponse> getDevicesByBrand(@PathVariable String brand) {
        log.debug("getDevicesByBrand(brand={})", brand);
        return deviceService.getDevicesByBrand(brand);
    }

    @Operation(summary = "Fetches devices by state")
    @GetMapping(path = "/{state}")
    public List<DeviceResponse> getDevicesByState(@PathVariable String state) {
        log.debug("getDevicesByState(state={})", state);
        return deviceService.getDevicesByState(state);
    }

    @Operation(summary = "Deletes a device")
    @GetMapping(path = "/{deviceId}")
    public void deleteDevice(@PathVariable Long deviceId) {
        log.debug("deleteDevice(deviceId={})", deviceId);
        deviceService.deleteDevice(deviceId);
    }
}
