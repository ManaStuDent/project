package com.manastudent.db.dao.ex;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ResourceMapperEx {

    // result:  ADMINE,USER
    @Select("select group_concat(ro.name) From " +
            "u_role ro LEFT JOIN u_role_resource rr " +
            "on ro.id = rr.role_id " +
            "LEFT JOIN u_resource r " +
            "on rr.resource_id = r.id " +
            "where r.resource = #{resourceUrl} ")
    String queryRoleNameByResource(String resourceUrl);


}
