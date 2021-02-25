package com.manastudent.db.dao.ex;

import com.manastudent.db.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapperEx {
    User findByid(int id);

    @Select("select * from u_user where id = #{id}")
    User findByIdWithMapper(int id);
}