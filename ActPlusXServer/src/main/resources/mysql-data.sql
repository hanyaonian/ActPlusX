SET SESSION storage_engine = "InnoDB";
SET SESSION time_zone = "+0:00";
ALTER DATABASE CHARACTER SET "utf8";

-- 角色
insert into Role values(0, "超级管理员,actPlus平台管理者", "admin");
insert into Role values(1, "活动发布者,包括社团活动发布者和临时的申请的发布者", "publisher");
insert into Role values(2, "普通用户,浏览参加活动和发布参加组队", "user");

--权限
--超级管理员
insert into Permission values(0, "审查活动", "admin:audit");
--社团管理员
insert into Permission values(1, "发布活动", "publisher:publish");
--普通用户
insert into Permission values(2, "报名活动", "user:join");

--角色权限表
-- 超级管理员可审核，发布活动
insert into Role_Permission values(0, 0, 0);
insert into Role_Permission values(1, 1, 0);
-- 发布者
insert into Role_Permission values(2, 1, 1);
-- 普通用户
insert into Role_Permission values(3, 2, 2);

--用户角色表
insert into User_Role values(0, 0, "young");
insert into User_Role values(1, 1, "young");
insert into User_Role values(2, 2, "young");
insert into User_Role values(3, 0, "youngdou1");
insert into User_Role values(4, 1, "youngdou2");
insert into User_Role values(5, 2, "youngdou3");

-- 活动发布者
insert into Publisher values(0, "gongyi.jpg", "中大青协", 0, "公益");
insert into Publisher values(1, "paobu.jpg", "中大跑步协会", 0, "公益");
-- 发布者用户
insert into User_Publisher values(0, 0, "young");
insert into User_Publisher values(1, 1, "youngdou2");

-- 学校
insert into School values(0, "广州", "sysu.jpg" , "广东", "东校区");
insert into School values(1, "广州", "sysu.jpg" , "广东", "南校区");
insert into School values(2, "广州", "sysu.jpg" , "广东", "北校区");
insert into School values(3, "广州", "sysu.jpg" , "广东", "珠海校区");
insert into School values(4, "广州", "sysu.jpg" , "广东", "全部校区");

--用户
insert into User values(0, "yaoyongdou@qq.com", "youngdou-head.jpg", "youngdou", "5F4DCC3B5AA765D61D8327DEB882CF99", "F", "young");
insert into User values(3, "douyaoyong@163.com", "youngdou-head.jpg", "youngdou", "5F4DCC3B5AA765D61D8327DEB882CF99", "F", "youngdou3");

insert into User values(1, "youngdou-head.jpg", "youngdou", "5F4DCC3B5AA765D61D8327DEB882CF99", "F", "youngdou", "yaoyongdou@qq.com");

