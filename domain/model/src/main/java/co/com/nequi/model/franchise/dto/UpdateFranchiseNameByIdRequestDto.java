package co.com.nequi.model.franchise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFranchiseNameByIdRequestDto {
    public Long id;
    public String name;
}
