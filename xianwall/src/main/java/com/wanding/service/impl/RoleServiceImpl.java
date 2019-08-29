package com.wanding.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanding.dao.RoleMapper;
import com.wanding.model.Role;
import com.wanding.service.PageService;
import com.wanding.service.RoleService;

@Service(value = "roleService")
public class RoleServiceImpl extends PageService  implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

	@Override
	public ArrayList<Role> findAllRoles() {
		return roleMapper.findAllRoles();
	}

}
