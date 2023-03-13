CREATE TABLE IF NOT EXISTS public.catalog (
	id int4 NULL,
	"name" varchar NULL,
	"type" varchar NULL,
	quantity int4 NULL
);

INSERT INTO public.catalog
	(id, "name", "type", quantity)
values
	(0, 'Indomie', 'Food', 322),
	(1, 'Teh Kotak', 'Drink', 467),
	(2, 'Beng-Beng', 'Snack', 598),
	(3, 'Tepung', 'Ingredient', 120),
	(4, 'Beras', 'Ingredient', 60),
	(5, 'Silverqueen', 'Snack', 430);