package challenge.devices.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceRequest {

    @Schema(description = "Device name", example = "Router", type = "String")
    private String name;

    @Schema(description = "Device brand", example = "Nokia", type = "String")
    private String brand;

    @Schema(description = "Device state", example = "Available",
            type = "String", allowableValues = { "Available", "In_use", "Inactive" })
    @Pattern(regexp = "Available|In_use|Inactive")
    private String state;
}
