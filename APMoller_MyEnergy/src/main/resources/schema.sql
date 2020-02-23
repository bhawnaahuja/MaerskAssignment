
 

create table price_plan
(
  
   price_plan_name varchar(255) not null,
   unit_rate int not null,
   primary key(price_plan_name)
   
);


create table user_details
(
  
   smart_meter_id varchar not null,
   current_price_plan varchar not null,
   primary key(smart_meter_id)
   
);

create table METER_READING
(
  
   reading_id int not null AUTO_INCREMENT,
   smart_meter_id varchar references user_details(smart_meter_id),
   time_of_reading BIGINT  not null,
   reading double not null,
   primary key(reading_id));
   






