package co.com.nequi.api.dto;

import lombok.Getter;

@Getter
public class CreateBranchRequestDto {
    private Integer franchiseId;
    private String name;
    private String address;
    private String phoneNumber;
}
