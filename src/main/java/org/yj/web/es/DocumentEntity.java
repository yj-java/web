package org.yj.web.es;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class DocumentEntity {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
