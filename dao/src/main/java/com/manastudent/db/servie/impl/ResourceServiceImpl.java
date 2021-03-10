package com.manastudent.db.servie.impl;

import com.manastudent.db.dao.ex.ResourceMapperEx;
import com.manastudent.db.servie.ResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    ResourceMapperEx resourceMapperEx;

    @Override
    public String queryRoleNameByResource(String resourceUrl) {
        return resourceMapperEx.queryRoleNameByResource(resourceUrl);
    }
}
