package com.pluralsight.orderfulfillment.test;

import org.springframework.jdbc.core.JdbcTemplate;

public class DerbyDatabaseBean {
	private JdbcTemplate jdbcTemplate;

	public void create() {
		try {
			jdbcTemplate.execute("drop table orders.orderItem");
			jdbcTemplate.execute("drop table orders.\"order\"");
			jdbcTemplate.execute("drop table orders.catalogitem");
			jdbcTemplate.execute("drop table orders.customer");
			jdbcTemplate.execute("drop schema orders");
		} catch (Throwable e) {
			//ignore
		}

		jdbcTemplate.execute("CREATE SCHEMA orders");
		jdbcTemplate.execute(
				"create table orders.customer (id integer not null, firstName varchar(200) not null, lastName varchar(200) not null, email varchar(200) not null, primary key (id) )");
		jdbcTemplate.execute(
				"create table orders.catalogitem (id integer not null, itemNumber varchar(200) not null,itemName varchar(200) not null, itemType varchar(200) not null, primary key (id) )");
		jdbcTemplate.execute(
				"create table orders.\"order\" (id integer not null, customer_id integer not null,orderNumber varchar(200) not null, timeOrderPlaced timestamp not null,lastUpdate timestamp not null, status varchar(200) not null, primary key (id))");
		jdbcTemplate
				.execute("alter table orders.\"order\" add constraint orders_fk_1 foreign key (customer_id) references orders.customer (id)");
		jdbcTemplate.execute(
				"create table orders.orderItem (id integer not null, order_id integer not null, catalogitem_id integer not null, status varchar(200) not null, price decimal(20,5), lastUpdate timestamp not null, quantity integer not null, primary key (id) )");
		jdbcTemplate
				.execute("alter table orders.orderItem add constraint orderItem_fk_1 foreign key (order_id) references orders.\"order\" (id)");
		jdbcTemplate.execute(
				"alter table orders.orderItem add constraint orderItem_fk_2 foreign key (catalogitem_id) references orders.catalogitem (id)");

	}

	public void destroy() {
		try {
			jdbcTemplate.execute("drop table orders.orderItem");
			jdbcTemplate.execute("drop table orders.\"order\"");
			jdbcTemplate.execute("drop table orders.catalogitem");
			jdbcTemplate.execute("drop table orders.customer");
			jdbcTemplate.execute("drop schema orders");
		} catch (Throwable e) {
			//ignore
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
