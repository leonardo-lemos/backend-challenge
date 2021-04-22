CREATE TABLE tbl_deliveries (
                                       id_delivery int8 NOT NULL,
                                       tp_mode varchar(255) NULL,
                                       id_order int8 NULL,
                                       CONSTRAINT tbl_deliveries_pkey PRIMARY KEY (id_delivery),
                                       CONSTRAINT fk_delivery_x_order_01 FOREIGN KEY (id_order) REFERENCES tbl_orders(id_order)
);

CREATE SEQUENCE seq_deliveries
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;