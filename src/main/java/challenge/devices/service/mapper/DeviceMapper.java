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
        return device;
    }

    public Device toUpdatedDevice(UpdateDeviceRequest request) {
        Device device = new Device();
        device.setName(request.getName());
        device.setBrand(request.getBrand());
        device.setState(Device.State.valueOf(request.getState()));
        return device;
    }

    public Device toPartiallyUpdatedDevice(UpdateDeviceRequest request) {
        Device device = new Device();
        if (request.getBrand() != null) device.setBrand(request.getBrand());
        if (request.getName() != null) device.setName(request.getName());
        if (request.getState() != null) device.setState(Device.State.valueOf(request.getState()));
        return device;
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
