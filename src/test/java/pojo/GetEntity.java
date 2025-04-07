package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetEntity {

    @Builder.Default
    private String additional_info = "Тестовая сущность";

    @Builder.Default
    private String title = "тест";

}
