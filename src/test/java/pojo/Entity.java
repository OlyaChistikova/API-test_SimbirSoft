package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {

    private Integer id;
    private Addition addition;
    private String importantNumbers;
    private String title;
    private Boolean verified;

}
