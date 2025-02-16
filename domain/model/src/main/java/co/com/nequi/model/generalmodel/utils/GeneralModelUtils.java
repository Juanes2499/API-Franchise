package co.com.nequi.model.generalmodel.utils;

import co.com.nequi.model.generalmodel.GeneralModel;

public class GeneralModelUtils {
    public static GeneralModel mapToGeneralModel(String method, String message, Boolean success) {
        return GeneralModel.builder()
                .method(method)
                .message(message)
                .success(success)
                .build();
    }
}
