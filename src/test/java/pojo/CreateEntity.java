package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CreateEntity {

    @Builder.Default
    private String additional_info = "Тестовая сущность";

    @Builder.Default
    private String title = "тест";

    @Builder.Default
    private boolean verified = true;
}
