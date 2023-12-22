package val.gord.blogproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String comment;

    //relationship: ManytoOne
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;


}
