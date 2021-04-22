CREATE TABLE crz_product_order (
                                          id_product_order int8 NOT NULL,
                                          ds_amount numeric(19,2) NULL,
                                          ds_units numeric(19,2) NULL,
                                          id_order int8 NULL,
                                          id_product int8 NULL,
                                          CONSTRAINT crz_product_order_pkey PRIMARY KEY (id_product_order),
                                          CONSTRAINT fk_product_order_x_order_01 FOREIGN KEY (id_order) REFERENCES tbl_orders(id_order),
                                          CONSTRAINT fk_product_order_x_product_01 FOREIGN KEY (id_product) REFERENCES tbl_products(id_product)
);

CREATE SEQUENCE seq_product_order
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;