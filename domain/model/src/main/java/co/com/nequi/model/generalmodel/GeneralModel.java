package co.com.nequi.model.generalmodel;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GeneralModel {
    private String method;
    private String message;
    private Boolean success;
}
