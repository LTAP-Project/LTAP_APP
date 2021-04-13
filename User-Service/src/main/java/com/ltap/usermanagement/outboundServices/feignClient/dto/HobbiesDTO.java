package com.ltap.usermanagement.outboundServices.feignClient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HobbiesDTO {
    @Id
    private String id;

    private String name;

}
