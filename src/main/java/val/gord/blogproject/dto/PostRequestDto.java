package val.gord.blogproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import val.gord.blogproject.validators.UniqueTitle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {

    @NotNull
    @Size(min = 2,max = 255)
    @UniqueTitle
    private  String title;
    @NotNull
    @Size(min = 2,max = 1000)
    private  String description;
    @NotNull
    @Size(min = 2)
    private  String content;

}
