package com.justdo.system.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justdo.system.role.dao.RoleDao;
import com.justdo.system.role.dao.RoleMenuDao;
import com.justdo.system.user.dao.UserDao;
import com.justdo.system.user.dao.UserRoleDao;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.domain.RoleMenuDO;
import com.justdo.system.role.service.RoleService;

/**
 * 角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleDao roleMapper;
    @Autowired
    RoleMenuDao roleMenuMapper;
    @Autowired
    UserDao userMapper;
    @Autowired
    UserRoleDao userRoleMapper;

    @Override
    public List<RoleDO> list() {
        List<RoleDO> roles = roleMapper.list(new HashMap<>(16));
        return roles;
    }


    @Override
    public List<RoleDO> list(String userId) {
        List<String> rolesIds = userRoleMapper.listRoleId(userId);
        List<RoleDO> roles = roleMapper.list(new HashMap<>(16));
        for (RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (String roleId : rolesIds) {
                if (Objects.equals(roleDO.getRoleId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
    @Transactional
    @Override
    public int save(RoleDO role) {
        int count = roleMapper.save(role);
        List<String> menuIds = role.getMenuIds();
        String roleId = role.getRoleId();
        List<RoleMenuDO> rms = new ArrayList<>();
        for (String menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.delByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return count;
    }

    @Transactional
    @Override
    public int del(String id) {
        int count = roleMapper.del(id);
        userRoleMapper.delByRoleId(id);
        roleMenuMapper.delByRoleId(id);
        return count;
    }

    @Override
    public RoleDO get(String id) {
        RoleDO roleDO = roleMapper.get(id);
        return roleDO;
    }

    @Override
    public int update(RoleDO role) {
        int r = roleMapper.update(role);
        List<String> menuIds = role.getMenuIds();
        String roleId = role.getRoleId();
        roleMenuMapper.delByRoleId(roleId);
        List<RoleMenuDO> rms = new ArrayList<>();
        for (String menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return r;
    }

    @Override
    public int batchDel(String[] ids) {
        int r = roleMapper.batchDel(ids);
        return r;
    }

}
