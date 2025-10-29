package challenge.devices.service.mapper;

import challenge.devices.domain.Device;
import challenge.devices.dto.CreateDeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.dto.UpdateDeviceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceMapper {

    public Device toDevice(CreateDeviceRequest request) {
        Device device = new Device();
        device.setName(request.getName());
        device.setBrand(request.getBrand());
        device.setState(Device.State.Available);
        return device;
    }

    public Device toUpdatedDevice(Device existingDevice, UpdateDeviceRequest request) {
        existingDevice.setName(request.getName());
        existingDevice.setBrand(request.getBrand());
        existingDevice.setState(Device.State.valueOf(request.getState()));
        return existingDevice;
    }

    public Device toPartiallyUpdatedDevice(Device existingDevice, UpdateDeviceRequest request) {
        if (request.getBrand() != null) existingDevice.setBrand(request.getBrand());
        if (request.getName() != null) existingDevice.setName(request.getName());
        if (request.getState() != null) existingDevice.setState(Device.State.valueOf(request.getState()));
        return existingDevice;
    }

    public DeviceResponse toResponse(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .state(String.valueOf(device.getState()))
                .build();
    }
}
