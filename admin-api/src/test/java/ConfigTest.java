import com.manastudent.admin.Application;
import com.manastudent.admin.entity.User;
import com.manastudent.core.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ConfigTest {

    @Test
    public void jsonTest(){
        User user = new User();
        user.setId(1);
        user.setValidator(true);
        user.setCreateDate(new Date());

        String json = JacksonUtil.obj2String(user);

        String jsonStr = "{\"id\":1,\"validator\":true,\"createDate\":\"2021-02-24 11:22:01\"}";
        User userFormJson = JacksonUtil.string2Obj(jsonStr, User.class);
        assert userFormJson.getId().equals(1);

        String id = JacksonUtil.parseString(jsonStr, "id");
        assert id.equals("1");

    }

}
