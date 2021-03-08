package com.manastudent.db.servie;

import com.manastudent.db.domain.Role;
import com.manastudent.db.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface UserService {

    User getCurrentUser();

    String getCurrentUserName();

    User findById(@NotNull int id);

    User findByLoginName(@NotNull String loginName);

    User findByIdWithMapper(@NotNull int id);

    List<Role> getAllRoles(@NotNull int id);
}
