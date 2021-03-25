package com.manastudent.db.dao.ex;

import com.manastudent.db.domain.Role;
import com.manastudent.db.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapperEx {
    User findById(int id);

    @Select("select * from u_user where id = #{id}")
    User findByIdWithMapper(int id);

    @Select("select r.* from u_user u LEFT JOIN u_user_role ur on u.id = ur.user_id left JOIN u_role r on ur.role_id = r.id where u.id = #{id}")
    List<Role> getAllRoles(Integer id);

    @Select("select * from u_user where name = #{loginName}")
    User findByLoginName(String loginName);

    void batchInsertUser(List<User> list);
}