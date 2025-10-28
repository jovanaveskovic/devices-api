package challenge.devices.service;

import challenge.devices.domain.Device;
import challenge.devices.dto.CreateDeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.dto.UpdateDeviceRequest;
import challenge.devices.exception.DeviceInUseException;
import challenge.devices.exception.DeviceNotFoundException;
import challenge.devices.repository.DeviceRepository;
import challenge.devices.service.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultDeviceService implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public DeviceResponse createDevice(CreateDeviceRequest request) {
        log.debug("createDevice(request={})", request);
        Device device = deviceMapper.toDevice(request);
        return transformToResponse(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public DeviceResponse updateDevice(Long id, UpdateDeviceRequest request) {
        log.debug("updateDevice(id={},request={})", id, request);
        Device device = getDeviceOrThrowException(id);
        validateDeviceUpdate(device, request);
        device = deviceMapper.toUpdatedDevice(request);
        return transformToResponse(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public DeviceResponse partialUpdateDevice(Long id, UpdateDeviceRequest request) {
        log.debug("partialUpdateDevice(id={},request={})", id, request);
        Device device = getDeviceOrThrowException(id);
        validatePartialDeviceUpdate(device, request);
        device = deviceMapper.toPartiallyUpdatedDevice(request);
        return transformToResponse(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public DeviceResponse getDevice(Long id) {
        log.debug("getDevice(id={})", id);
        return transformToResponse(getDeviceOrThrowException(id));
    }

    @Override
    @Transactional
    public List<DeviceResponse> getAllDevices() {
        log.debug("getAllDevices()");
        return deviceRepository.findAll()
                .stream().map(this::transformToResponse).collect(toList());
    }

    @Override
    @Transactional
    public List<DeviceResponse> getDevicesByBrand(String brand) {
        log.debug("getDevicesByBrand(brand={})", brand);
        return deviceRepository.findByBrand(brand)
                .stream().map(this::transformToResponse).collect(toList());
    }

    @Override
    @Transactional
    public List<DeviceResponse> getDevicesByState(String state) {
        log.debug("getDevicesByState(state={})", state);
        return deviceRepository.findByState(Device.State.valueOf(state))
                .stream().map(this::transformToResponse).collect(toList());
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        log.debug("deleteDevice(id={})", id);
        Device device = getDeviceOrThrowException(id);
        if (device.getState() == Device.State.IN_USE) {
            throw new DeviceInUseException("Cannot delete device in use");
        }
        deviceRepository.delete(device);
    }

    private DeviceResponse transformToResponse(Device device) {
        return deviceMapper.toResponse(device);
    }

    private Device getDeviceOrThrowException(Long deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(
                () -> new DeviceNotFoundException("Device not found"));
    }

    private void validateDeviceUpdate(Device device, UpdateDeviceRequest request){
        if (device.getState() == Device.State.IN_USE) {
            if (!device.getName().equals(request.getName()) ||
                    !device.getBrand().equals(request.getBrand())) {
                throw new DeviceInUseException("Cannot update name or brand of a device in use");
            }
        }
    }

    private void validatePartialDeviceUpdate(Device device, UpdateDeviceRequest request){
        if (device.getState() == Device.State.IN_USE) {
            if (request.getName() != null && !device.getName().equals(request.getName()) ||
                    request.getBrand() != null && !device.getBrand().equals(request.getBrand())) {
                throw new DeviceInUseException("Cannot update name or brand of a device in use");
            }
        }
    }
}
