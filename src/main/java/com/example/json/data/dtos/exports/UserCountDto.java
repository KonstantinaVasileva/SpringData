package com.example.json.data.dtos.exports;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UserCountDto implements Serializable {
    @Expose
    private int count;
    @Expose
    private Set<UserDto> users;

    public UserCountDto() {
        this.users = new HashSet<>();
    }
}
