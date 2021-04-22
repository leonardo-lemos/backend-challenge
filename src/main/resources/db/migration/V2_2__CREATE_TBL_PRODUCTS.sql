CREATE TABLE tbl_products
(
    id_product      int8           NOT NULL,
    ds_barcode      varchar(128)   NOT NULL,
    ds_product      varchar(255)   NOT NULL,
    nm_product      varchar(255)   NOT NULL,
    ds_unit_price   numeric(19, 2) NOT NULL,
    id_manufacturer int8           NOT NULL,
    CONSTRAINT tbl_products_pkey PRIMARY KEY (id_product),
    CONSTRAINT fk_product_x_manufacturer_01 FOREIGN KEY (id_manufacturer) REFERENCES tbl_manufacturers (id_manufacturer)
);

CREATE SEQUENCE seq_products
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;