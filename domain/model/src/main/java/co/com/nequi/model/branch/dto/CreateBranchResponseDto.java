package co.com.nequi.model.branch.dto;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.franchise.Franchise;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchResponseDto {
    private Long id;
    private Franchise franchise;
    private String name;
    private String address;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public void BuildCreateBranchResponseDto (Branch branch, Franchise franchise){
        this.setId(branch.getId());
        this.setFranchise(franchise);
        this.setName(branch.getName());
        this.setAddress(branch.getAddress());
        this.setPhoneNumber(branch.getPhoneNumber());
        this.setCreatedAt(branch.getCreatedAt());
        this.setUpdatedAt((branch.getUpdatedAt()));
    }
}
