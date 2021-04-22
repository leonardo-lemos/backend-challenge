CREATE TABLE tbl_manufacturers
(
    id_manufacturer int8 NOT NULL,
    nm_manufacturer varchar(255) NULL,
    CONSTRAINT tbl_manufacturers_pkey PRIMARY KEY (id_manufacturer)
);

CREATE SEQUENCE seq_manufacturers
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;