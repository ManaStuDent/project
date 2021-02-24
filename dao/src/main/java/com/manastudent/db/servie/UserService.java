package com.manastudent.db.servie;

import com.manastudent.db.domain.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User findById(int id);
}
