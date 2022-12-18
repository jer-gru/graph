-- @formatter:off
INSERT INTO public.node (id, name, created_at, updated_at) VALUES (1, 'A', '2022-12-18 10:49:37.000000', '2022-12-18 10:49:39.000000');
INSERT INTO public.node (id, name, created_at, updated_at) VALUES (2, 'B', '2022-12-18 10:49:47.000000', '2022-12-18 10:49:49.000000');

ALTER SEQUENCE node_seq RESTART WITH 51;