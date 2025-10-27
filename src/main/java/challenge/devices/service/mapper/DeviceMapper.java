package challenge.devices.service.mapper;

import challenge.devices.domain.Device;
import challenge.devices.dto.DeviceRequest;
import challenge.devices.dto.DeviceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceMapper {

    public Device toDevice(DeviceRequest request) {
        Device device = new Device();
        device.setName(request.getName());
        device.setBrand(request.getBrand());
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
