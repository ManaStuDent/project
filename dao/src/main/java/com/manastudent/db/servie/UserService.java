package com.manastudent.db.servie;

import com.manastudent.db.domain.Role;
import com.manastudent.db.domain.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User getCurrentUser();

    Optional<String> getCurrentUserName();

    User findById(@NotNull int id);

    User findByLoginName(@NotNull String loginName);

    User findByIdWithMapper(@NotNull int id);

    List<Role> getAllRoles(@NotNull int id);
}
