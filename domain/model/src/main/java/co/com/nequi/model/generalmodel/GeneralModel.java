package co.com.nequi.model.generalmodel;
import lombok.*;

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
