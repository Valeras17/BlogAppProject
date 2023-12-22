package val.gord.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CommentRequestDto {
    private String comment;
}
