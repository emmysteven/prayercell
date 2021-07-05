package cells.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("Male")
    MALE,

    @JsonProperty("Female")
    FEMALE
}
