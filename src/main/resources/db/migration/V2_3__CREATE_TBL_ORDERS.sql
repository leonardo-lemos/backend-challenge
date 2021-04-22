CREATE TABLE tbl_orders
(
    id_order  int8         NOT NULL,
    ds_status varchar(255) NULL,
    CONSTRAINT tbl_orders_pkey PRIMARY KEY (id_order)
);

CREATE SEQUENCE seq_orders
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;