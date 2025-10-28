package challenge.devices.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceRequest {

    @NotEmpty
    @Schema(description = "Device name", example = "Router", type = "String")
    private String name;

    @NotEmpty
    @Schema(description = "Device brand", example = "Nokia", type = "String")
    private String brand;
}
