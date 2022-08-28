-- 创建初始的员工类
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '芦畅', '370902199510230073', '2004-12-29', '$2b$12$ng/BWaCbONMJBiWe8YbY4.VdHvWkB99jnfv2XXYKXjArvvzdOjF0m',
        '20221390', 'jing66@example.org', '13625749433', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '彭旭', '360828199001081053', '1983-12-04', '$2b$12$TrjFXCCClrFwupIHl4mh9u51rhP6XOhhH2MxDQAjxwPls1d/bJO7a',
        '20223395', 'qluo@example.net', '18622101447', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '张亮', '310116194010074722', '1973-09-04', '$2b$12$/VgrlmGKDrAfktsYQGEywOv2KGzdS5HVWr4blpUNVsUPf.EqS.Fl2',
        '20221375', 'xiulanzeng@example.net', '13423600682', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '马柳', '341622196011308498', '2005-05-17', '$2b$12$IsR6OsIJ/CmicLjPN0aqGuldpfxlFghvXoV7nTgKYgqIID5iU4V4.',
        '20221973', 'qiang22@example.com', '13355470169', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '李丽华', '620200197001087012', '1994-03-23', '$2b$12$4VHnadza7wSujjJ1djF58uvh3zWM1u93Gk9DNKEh9ObJu75pG9NWm',
        '20222143', 'xiuyinglei@example.org', '15317475176', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '王莉', '610124197509143648', '1989-04-24', '$2b$12$j0hh5msRvDC7bDMSFigaH.Bi2hMVw0WYjpkWNRNHI2G49FV5OB/ES',
        '20224804', 'xuewei@example.net', '18204706986', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '吴成', '610430193511122374', '1983-07-20', '$2b$12$hHQL/xgxtAfnw3X.U70ZX.RSFq72JSxCInAWaXyvtAqszKbd.Sg0G',
        '20222870', 'weiduan@example.com', '13922188526', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '赖军', '520200196107240267', '2014-05-02', '$2b$12$.E7OeUSFcy9swkk8csyu4uDidI0pmaBN91WbU6VCCSP0ZJ0qtr3Uy',
        '20221147', 'daiyong@example.com', '15792404507', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '曾红霞', '31010619411216016X', '2017-06-04', '$2b$12$63viMjH0ByleoQ30Lt3lWOtZpnElYcX8SiEwHhXEYduKMgu0RhyMq',
        '20223930', 'naliao@example.org', '18719188016', false);
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone, verified)
VALUES (null, '冼磊', '310105196301133892', '1990-09-09', '$2b$12$HWhuM6QFEQVc.6Qu60r1KunlcHojWRK4bXUNs747sPD4NRlj.DNKS',
        '20223187', 'tao34@example.org', '13588443941', false);

-- 创建最初的角色类，一个是ROOT；一个是用于测试的开发角色
INSERT INTO `role`(id, active, name, parent_id)
VALUES (null, false, 'ROOT', null);
INSERT INTO `role`(id, active, name, parent_id)
VALUES (null, true, 'DEV', null);
-- 添加经理角色和员工角色
INSERT INTO `role`(id, active, name, parent_id)
VALUES (null, true, 'manager', 1);
INSERT INTO `role`(id, active, name, parent_id)
VALUES (null, true, 'employee', 3);

-- 添加权限信息
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '测试', 'dev:test', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看员工列表', 'oa:employee:list', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看员工信息', 'oa:employee:view', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '修改员工信息', 'oa:employee:modify', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '删除员工信息', 'oa:employee:delete', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看角色列表', 'oa:role:list', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看角色信息', 'oa:role:view', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '修改角色信息', 'oa:role:modify', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '删除角色信息', 'oa:role:delete', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看资源列表', 'oa:permission:list', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看资源信息', 'oa:permission:view', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '修改资源信息', 'oa:permission:modify', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '删除资源信息', 'oa:permission:delete', null);
INSERT INTO `permission`(id, description, value, parent_id)
VALUES (null, '查看当前登录员工信息', 'oa:employee:view-current', null);

-- 添加员工和角色的映射信息
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (3, 1);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (3, 2);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 3);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 4);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 5);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 6);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 7);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 8);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 9);
INSERT INTO `role_employees`(roles_id, employees_id)
VALUES (4, 10);

-- 添加角色和权限的映射信息
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (3, 4);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (3, 5);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (3, 8);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (3, 9);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (3, 12);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (3, 13);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (4, 2);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (4, 3);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (4, 6);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (4, 7);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (4, 10);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (4, 11);
INSERT INTO `role_permissions`(roles_id, permissions_id)
VALUES (1, 14);

-- 添加页面资源
INSERT INTO `page_res`(id, description, page_group, icon, url, parent_id)
VALUES (null, '查看员工列表', false, 'mdi-account', '/employees', null);
INSERT INTO `role_page_res`(roles_id, page_res_id)
VALUES (3, 1);
