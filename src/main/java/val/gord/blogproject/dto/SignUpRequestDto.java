package val.gord.blogproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @NotNull
    @NotEmpty
    @Size(min = 2,max = 20)
    private String username;
    @NotNull
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 6,max = 20)
    private String password;
}
