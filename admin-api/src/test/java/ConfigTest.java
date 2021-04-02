import com.manastudent.admin.AdminApiApplication;
import com.manastudent.core.util.JacksonUtil;
import com.manastudent.db.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApiApplication.class)
public class ConfigTest {

    @Test
    public void jsonTest(){
        User user = new User();
        user.setId(1);
        user.setCreateTime(LocalDateTime.now());

        String json = JacksonUtil.obj2String(user);

        String jsonStr = "{\"id\":1,\"validator\":true,\"createDate\":\"2021-02-24 11:22:01\"}";
        User userFormJson = JacksonUtil.string2Obj(jsonStr, User.class);
        assert userFormJson.getId().equals(1);

        String id = JacksonUtil.parseString(jsonStr, "id");
        assert id.equals("1");

    }

}
