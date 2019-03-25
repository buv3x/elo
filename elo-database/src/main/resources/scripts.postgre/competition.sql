-- Table: public.competition

-- DROP TABLE public.competition;

CREATE TABLE public.competition
(
    name character varying(42) COLLATE pg_catalog."default" NOT NULL,
    gender smallint NOT NULL,
    date date NOT NULL,
    type smallint NOT NULL,
    level smallint NOT NULL,
    id integer NOT NULL DEFAULT nextval('competition_id_seq'::regclass),
    CONSTRAINT pk_comp_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.competition
    OWNER to postgres;