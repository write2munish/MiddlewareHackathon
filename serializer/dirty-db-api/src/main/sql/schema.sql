drop table dirty_list;

create table dirty_list(
  sno bigint auto_increment primary key,
  id varchar(20),
  `type` varchar(20),
  country varchar(2),
  language varchar(2),
  lastmodified timestamp,
  sequencenumber bigint
);