CREATE TABLE tbl_consumers (
                                      id_consumer int8 NOT NULL,
                                      ds_email varchar(254) NULL,
                                      nm_consumer varchar(128) NULL,
                                      ds_phone varchar(14) NULL,
                                      id_order int8 NULL,
                                      CONSTRAINT tbl_consumers_pkey PRIMARY KEY (id_consumer),
                                      CONSTRAINT fk_consumer_x_order_01 FOREIGN KEY (id_order) REFERENCES tbl_orders(id_order)
);

CREATE SEQUENCE seq_consumers
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;