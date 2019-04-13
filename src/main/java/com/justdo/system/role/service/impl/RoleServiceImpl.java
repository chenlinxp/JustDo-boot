package com.justdo.system.role.service.impl;

import com.justdo.system.employee.dao.EmployeeDao;
import com.justdo.system.employee.dao.EmployeeRoleDao;
import com.justdo.system.role.dao.RoleDao;
import com.justdo.system.role.dao.RoleResourceDao;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.domain.RoleResourceDO;
import com.justdo.system.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    RoleDao roleDao;
    @Autowired
    RoleResourceDao roleResourceDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    EmployeeRoleDao employeeRoleDao;

    @Override
    public List<RoleDO> list() {
        List<RoleDO> roles = roleDao.list(new HashMap<>(16));
        return roles;
    }


    @Override
    public List<RoleDO> list(String EmployeeId) {
        List<String> rolesIds = employeeRoleDao.listRoleId(EmployeeId);
        List<RoleDO> roles = roleDao.list(new HashMap<>(16));
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
        int count = roleDao.save(role);
        List<String> resourceIds = role.getResourceIds();
        String roleId = role.getRoleId();
        List<RoleResourceDO> roleResourceDOs = new ArrayList<>();
        for (String resourceId : resourceIds) {
            RoleResourceDO roleResourceDO = new RoleResourceDO();
            roleResourceDO.setRoleId(roleId);
            roleResourceDO.setResourceId(resourceId);
            roleResourceDOs.add(roleResourceDO);
        }
        roleResourceDao.delByRoleId(roleId);
        if (roleResourceDOs.size() > 0) {
            roleResourceDao.batchSave(roleResourceDOs);
        }
        return count;
    }

    @Transactional
    @Override
    public int del(String roleId) {
        int count = roleDao.del(roleId);
        employeeRoleDao.delByRoleId(roleId);
        roleResourceDao.delByRoleId(roleId);
        return count;
    }

    @Override
    public RoleDO get(String roleId) {
        RoleDO roleDO = roleDao.get(roleId);
        return roleDO;
    }

    @Override
    public int update(RoleDO role) {
        int r = roleDao.update(role);
        List<String> resourceIds = role.getResourceIds();
        String roleId = role.getRoleId();
        roleResourceDao.delByRoleId(roleId);
        List<RoleResourceDO> roleResourceDOs = new ArrayList<>();
        for (String resourceId : resourceIds) {
            RoleResourceDO roleResourceDO = new RoleResourceDO();
            roleResourceDO.setRoleId(roleId);
            roleResourceDO.setResourceId(resourceId);
            roleResourceDOs.add(roleResourceDO);
        }
        if (roleResourceDOs.size() > 0) {
            roleResourceDao.batchSave(roleResourceDOs);
        }
        return r;
    }

    @Override
    public int batchDel(String[] ids) {
        int r = roleDao.batchDel(ids);
        return r;
    }

}
