package excel;

import com.alibaba.excel.EasyExcel;
import com.manastudent.admin.AdminApiApplication;
import com.manastudent.db.dao.ex.UserMapperEx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApiApplication.class)
public class ExcelReadTest {

    @Autowired
    private UserMapperEx userMapperEx;

    @Test
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "excel" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ImportUser.class, new UserDataListener(userMapperEx)).sheet().doRead();
    }
}
