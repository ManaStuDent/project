package excel;

import lombok.Data;

@Data
public class ImportUser {
    private Integer id;
    private String name;
    private String password;
    private String enabled;
}
