package co.com.nequi.model.branch.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchRequestDto {
    private Long franchiseId;
    private String name;
    private String address;
    private String phoneNumber;
}
