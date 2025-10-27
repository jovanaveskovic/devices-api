package challenge.devices.service;

import challenge.devices.dto.DeviceRequest;
import challenge.devices.dto.DeviceResponse;

import java.util.List;

public interface DeviceService {
    DeviceResponse createDevice(DeviceRequest deviceRequest);
    DeviceResponse updateDevice(Long id, DeviceRequest deviceRequest);
    DeviceResponse getDevice(Long id);
    List<DeviceResponse> getAllDevices();
    List<DeviceResponse> getAllDevicesByBrand(String brand);
    List<DeviceResponse> getAllDevicesByState(String state);
    void deleteDevice(Long id);
}
