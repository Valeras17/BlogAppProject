package val.gord.blogproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString//relationships
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String username;

    private String email;
    private String comment;

    //relationship: ManytoOne
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;


}
