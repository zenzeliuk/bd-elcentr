package ua.com.elcentr.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Enclosure {

    private Integer id;
    private String name;
    private String code;
    private String url;
    private String imageUrl;
    private Integer height;
    private Integer width;
    private Integer depth;
    private Integer index_protection;

}
