package cells.application.payload.request;


import cells.domain.enums.DeviceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeviceInfo {

    @NotBlank(message = "Device id cannot be blank")
    @ApiModelProperty(value = "Device Id", required = true, dataType = "string", allowableValues = "Non empty string")
    private String deviceId;

    @NotNull(message = "Device type cannot be null")
    @ApiModelProperty(value = "Device type Android/iOS", required = true, dataType = "string", allowableValues =
            "DEVICE_TYPE_ANDROID, DEVICE_TYPE_IOS")
    private DeviceType deviceType;

    @NotBlank(message = "Device notification token can be null but not blank")
    @ApiModelProperty(value = "Device notification id", dataType = "string", allowableValues = "Non empty string")
    private String notificationToken;

    public DeviceInfo(String deviceId, DeviceType deviceTypem, String notificationToken) {
        this.deviceId = deviceId;
        deviceType = deviceType;
        notificationToken = notificationToken;
    }

}
