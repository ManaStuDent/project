package excel;

import com.alibaba.excel.EasyExcel;
import com.manastudent.admin.AdminApiApplication;
import com.manastudent.db.dao.UserMapper;
import com.manastudent.db.domain.User;
import com.manastudent.db.domain.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApiApplication.class)
public class ExcelWriteTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void complexHeadWrite() {
        String fileName = TestFileUtil.getPath() + "excel" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ImportUser.class).sheet("sheet1").doWrite(data());
    }

    private List<ImportUser> data() {

        List<User> users = userMapper.selectByExample(new UserExample());

        List<ImportUser> list = new ArrayList<>();
        users.forEach(user -> {
            ImportUser importUser = new ImportUser();
            importUser.setId(user.getId());
            importUser.setName(user.getName());
            importUser.setPassword(user.getPassword());
            importUser.setEnabled(user.getEnabled().toString());
            list.add(importUser);
        });

        return list;
    }
}
