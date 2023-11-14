package ManueleSeretti.u5w3d1.Payloads;

import ManueleSeretti.u5w3d1.Entities.StatoDevice;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record newDeviceDTO(
        @NotEmpty(message = "Il nome è un campo obbligatorio!")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String name,
        StatoDevice stato,
        @NotNull(message = "L'Id dello user è un campo obbligatorio!")
        long userID


) {
}