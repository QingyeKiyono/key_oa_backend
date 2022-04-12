-- 创建初始的员工类
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '葛瑞', '140601195510311195', '2011-05-10', 'yrurlg424d', '20222911', 'ming34@example.com', '18583797888');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '农鹏', '410622198502051777', '1970-08-17', '6pmvzff76t', '20224228', 'yonghe@example.net', '18088734354');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '杨兵', '231202196111241152', '2000-03-16', 'nwizlpqp52', '20224767', 'xfeng@example.com', '15618769561');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '唐洋', '41050519730216392X', '2007-09-25', 'wvgbh8s24q', '20222655', 'majie@example.net', '13955798773');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '黄雪梅', '430200194004159369', '2020-01-18', 'cno4zu6qp9', '20222675', 'fengna@example.org', '13132322916');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '鲁红梅', '320981197103227712', '1982-08-03', '46ogs6hfnk', '20224960', 'lei29@example.com', '13674681660');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '王玉梅', '451301200011168217', '2002-09-08', 'e42ukcovck', '20221153', 'iyang@example.com', '13094719671');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '赵强', '141129198304090591', '1995-03-03', 'f50tszlyq0', '20224876', 'xia00@example.org', '18249888700');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '张建国', '520323194208039598', '1983-08-14', 'feljgcq81k', '20221367', 'xiulanlei@example.net',
        '15953757291');
INSERT INTO `employee`(id, name, identity, birthday, password, job_number, email, phone)
VALUES (null, '李涛', '441701200104284567', '2004-11-04', '5qbgam9312', '20223864', 'weiqian@example.net', '13360483849');

-- this is a sql comment
INSERT INTO `role`(id, name, parent_id)
VALUES (1, 'ROOT', 1);
