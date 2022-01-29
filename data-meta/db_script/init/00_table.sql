create table database_source
(
    id            int auto_increment comment '自增列，唯一标识'
        primary key,
    database_name text      null comment '数据库名称',
    url           text      not null comment '数据库连接地址',
    username      text      null comment '数据库用户名',
    pazzword      text      null comment '数据库密码',
    driver        text      null comment 'jdbc驱动包',
    instruction   text      null comment '说明',
    create_time   timestamp null comment '创建时间',
    update_time   timestamp null comment '更新时间',
    status        int       null comment '状态：0-停用,1-启用'
)
    comment '数据源信息';


create table database_table
(
    tab_id             int auto_increment
        primary key,
    tab_name           text        null comment '数据库表名称',
    database_source_id int         null comment '数据源ID',
    instruction        text        null comment '说明',
    alias_name         text        null comment '别名',
    version            text        null comment '版本信息',
    creat_time         timestamp   null comment '创建时间',
    schema_name        varchar(64) null
)
    comment '元数据表';

create table database_table_column
(
    id          int auto_increment comment '自增列'
        primary key,
    tab_id      int  null comment '数据库表ID',
    col_id      int  null comment '字段ID',
    col_name    text null comment '字段名称',
    col_type    text null comment '字段类型',
    col_length  int  null comment '字段长度',
    col_decimal int  null comment '精度'
)
    comment '数据库表字段';
