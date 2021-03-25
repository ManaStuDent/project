package excel;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.manastudent.core.util.BatchInsertConsumer;
import com.manastudent.db.dao.ex.UserMapperEx;
import com.manastudent.db.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDataListener extends AnalysisEventListener<ImportUser> {

    Log log = LogFactory.get();

    private UserMapperEx userMapperEx;

    // 内存中存储的 excel 行数，如果数据量过大可以适量减少一次读取到内存中的数据量
    private static final int BATCH_COUNT = 3000;
    List<User> dataList = new ArrayList<>();

    public UserDataListener() {
    }

    public UserDataListener(UserMapperEx userMapperEx) {
        this.userMapperEx = userMapperEx;
    }

    /**
     * 保存的业务逻辑
     */
    private void saveData() {
        BatchInsertConsumer.insertData(dataList, userMapperEx::batchInsertUser);
    }

    @Override
    public void invoke(ImportUser data, AnalysisContext analysisContext) {

        User user = new User();
        user.setName(data.getName());
        user.setPassword(data.getPassword());
        user.setEnabled(data.getEnabled().equals("1"));
        user.setCreateTime(LocalDateTime.now());

        dataList.add(user);
        user = null;
        if (dataList.size() >= BATCH_COUNT) {
            saveData();
            dataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        dataList.clear();
        log.info("导入完成");
    }
}
