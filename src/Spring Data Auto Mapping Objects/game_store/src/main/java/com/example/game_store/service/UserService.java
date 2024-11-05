package com.example.game_store.service;

import com.example.game_store.data.dto.UserLogInDTO;
import com.example.game_store.data.dto.UserRegisterDTO;
import com.example.game_store.data.entity.User;

public interface UserService {

    String userRegister(UserRegisterDTO userRegisterDTO);

    String userLogin(UserLogInDTO userLogInDTO);

    String userLogout();

    User getLoggedIn();

}
