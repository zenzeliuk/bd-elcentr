package ua.com.elcentr.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Object {

    private Integer id;
    private String name;
    private String notes;

}
