CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.tb_product (
	id int4 NOT NULL,
	category varchar(255) NULL,
	"name" varchar(255) NULL,
	price float8 NULL,
	quantity int4 NOT NULL,
	tenant_id uuid NULL,
	uuid uuid NULL,
	CONSTRAINT tb_product_pkey PRIMARY KEY (id)
);