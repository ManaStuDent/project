package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

@Data
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 1)
public class ImportUser {
    @ExcelProperty({"导出数据", "主键"})
    private Integer id;
    @ExcelProperty({"导出数据", "姓名"})
    private String name;
    @ColumnWidth(50)
    @ExcelProperty({"导出数据", "密码"})
    private String password;
    @ExcelProperty({"导出数据", "是否可用"})
    private String enabled;
}
