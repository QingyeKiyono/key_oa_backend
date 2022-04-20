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

-- this is a sql comment
INSERT INTO `role`(id, name, parent_id)
VALUES (1, 'ROOT', 1);
