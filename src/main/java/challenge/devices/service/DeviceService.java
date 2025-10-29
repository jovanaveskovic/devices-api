package challenge.devices.service;

import challenge.devices.dto.CreateDeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.dto.UpdateDeviceRequest;

import java.util.List;

public interface DeviceService {
    DeviceResponse createDevice(CreateDeviceRequest request);
    DeviceResponse updateDevice(Long id, UpdateDeviceRequest request);
    DeviceResponse partialUpdateDevice(Long id, UpdateDeviceRequest requests);
    DeviceResponse getDevice(Long id);
    List<DeviceResponse> getDevices(String brand, String state);
    void deleteDevice(Long id);
}
