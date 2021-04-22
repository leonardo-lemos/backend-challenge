CREATE TABLE tbl_payments
(
    id_payment           int8           NOT NULL,
    ds_amount            numeric(19, 2) NULL,
    ds_installment_value numeric(19, 2) NULL,
    ds_installments      int4           NULL,
    tp_mode              varchar(255)   NULL,
    id_order             int8           NULL,
    CONSTRAINT tbl_payments_pkey PRIMARY KEY (id_payment),
    CONSTRAINT fk_payment_x_order_01 FOREIGN KEY (id_order) REFERENCES tbl_orders (id_order)
);

CREATE SEQUENCE seq_payments
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;