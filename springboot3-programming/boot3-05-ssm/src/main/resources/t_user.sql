-- 會員表，並與部門表關聯
CREATE TABLE t_user (
    id SERIAL PRIMARY KEY,
    login_name VARCHAR(200),
    nick_name VARCHAR(200) DEFAULT '',
    passwd VARCHAR(200)
);
INSERT INTO t_user(login_name, nick_name, passwd) VALUES ('zhangsan', '張三', '123456');
