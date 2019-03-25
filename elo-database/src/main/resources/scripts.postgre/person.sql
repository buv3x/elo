-- Table: public.person

-- DROP TABLE public.person;

CREATE TABLE public.person
(
    name character varying(42) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(42) COLLATE pg_catalog."default" NOT NULL,
    gender smallint NOT NULL,
    id integer NOT NULL DEFAULT nextval('person_id_seq'::regclass),
    CONSTRAINT pk_pers_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.person
    OWNER to postgres;