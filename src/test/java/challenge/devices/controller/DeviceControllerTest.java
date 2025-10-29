package challenge.devices.controller;

import challenge.devices.dto.CreateDeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.dto.UpdateDeviceRequest;
import challenge.devices.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class DeviceControllerTest {

    private static final String PATH = "/api/v1/devices";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected DeviceService deviceService;

    @Test
    void shouldCreateDevice() throws Exception {
        Long id = 123L;
        String name = "device-name";
        String brand = "device-brand";
        String state = "Available";
        CreateDeviceRequest deviceRequest =
                CreateDeviceRequest.builder().name(name).brand(brand).build();
        DeviceResponse deviceResponse =
                DeviceResponse.builder().id(id).name(name).brand(brand).state(state).build();
        when(deviceService.createDevice(deviceRequest)).thenReturn(deviceResponse);

        ResultActions actualResult = this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceRequest))
        );

        actualResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.brand", is(brand)))
                .andExpect(jsonPath("$.state", is(state)));
    }

    @Test
    void shouldUpdateDevice() throws Exception {
        Long id = 123L;
        String name = "device-name";
        String brand = "device-brand";
        String state = "In_use";
        UpdateDeviceRequest deviceRequest =
                UpdateDeviceRequest.builder().name(name).brand(brand).state(state).build();
        DeviceResponse deviceResponse =
                DeviceResponse.builder().id(id).name(name).brand(brand).state(state).build();
        when(deviceService.updateDevice(id, deviceRequest)).thenReturn(deviceResponse);

        ResultActions actualResult = this.mockMvc.perform(put(PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceRequest))
        );

        actualResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.brand", is(brand)))
                .andExpect(jsonPath("$.state", is(state)));
    }

    @Test
    void shouldPartiallyUpdateDevice() throws Exception {
        Long id = 123L;
        String name = "device-name";
        String brand = "device-brand";
        String state = "In_use";
        UpdateDeviceRequest deviceRequest =
                UpdateDeviceRequest.builder().state(state).build();
        DeviceResponse deviceResponse =
                DeviceResponse.builder().id(id).name(name).brand(brand).state(state).build();
        when(deviceService.partialUpdateDevice(id, deviceRequest)).thenReturn(deviceResponse);

        ResultActions actualResult = this.mockMvc.perform(patch(PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceRequest))
        );

        actualResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.brand", is(brand)))
                .andExpect(jsonPath("$.state", is(state)));
    }

    @Test
    void shouldGetDevice() throws Exception {
        Long id = 123L;
        String name = "device-name";
        String brand = "device-brand";
        String state = "Available";
        DeviceResponse deviceResponse =
                DeviceResponse.builder().id(id).name(name).brand(brand).state(state).build();
        when(deviceService.getDevice(id)).thenReturn(deviceResponse);

        ResultActions actualResult = this.mockMvc.perform(get(PATH + "/" + id));

        actualResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.brand", is(brand)))
                .andExpect(jsonPath("$.state", is(state)));
    }

    @Test
    void shouldGetAllDevices() throws Exception {
        Long id = 123L;
        String name = "device-name";
        String brand = "device-brand";
        String state = "Available";
        DeviceResponse deviceResponse =
                DeviceResponse.builder().id(id).name(name).brand(brand).state(state).build();
        when(deviceService.getDevices(null, null)).thenReturn(Collections.singletonList(deviceResponse));

        ResultActions actualResult = this.mockMvc.perform(get(PATH));

        actualResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].brand", is(brand)))
                .andExpect(jsonPath("$[0].state", is(state)));
    }

    @Test
    void shouldGetAllDevicesByParameters() throws Exception {
        Long id = 123L;
        String name = "device-name";
        String brand = "device-brand";
        String state = "Available";
        DeviceResponse deviceResponse =
                DeviceResponse.builder().id(id).name(name).brand(brand).state(state).build();
        when(deviceService.getDevices(brand, state)).thenReturn(Collections.singletonList(deviceResponse));

        ResultActions actualResult = this.mockMvc.perform(get(PATH + "?brand=" + brand + "&state=" + state));

        actualResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].brand", is(brand)))
                .andExpect(jsonPath("$[0].state", is(state)));
    }


    @Test
    void shouldDeleteDevice() throws Exception {
        Long id = 123L;

        ResultActions actualResult = this.mockMvc.perform(delete(PATH + "/" + id));

        actualResult
                .andDo(print())
                .andExpect(status().isOk());

        verify(deviceService).deleteDevice(id);
    }
}
