package ua.com.elcentr.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer id;
    private Integer customerId;
    private Integer objectId;
    private Integer enclosureId;
    private Long creationTime;
    private String code;
    private Integer quantity;
    private String name;
    private String passport;
    private String photo;
    private Integer nominalCurrent;
    private Integer ingressProtection;
    private String decimalNumber;

}
