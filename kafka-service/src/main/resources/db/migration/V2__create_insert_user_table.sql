CREATE TABLE "user" (
	id int4 NULL,
	"name" varchar NULL,
	email varchar NULL,
	orderTimes int4 NULL
);

INSERT INTO "user"
	(id, "name", email, orderTimes)
values
	(0, 'User ke 1', 'userke1@gmail', 0),
	(1, 'User ke 2', 'userke2@gmail', 0),
	(2, 'Ujang', 'ujang@gmail', 0);