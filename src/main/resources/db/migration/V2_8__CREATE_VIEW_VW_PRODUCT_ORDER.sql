create or replace view vw_product_order as
select pdo.id_product as id,
       prd.nm_product as ds_name,
       pdo.ds_units,
       prd.ds_unit_price,
       pdo.ds_amount,
       pdo.id_order
from crz_product_order pdo
         inner join tbl_products prd on prd.id_product = pdo.id_product