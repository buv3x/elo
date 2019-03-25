-- Table: public.result

-- DROP TABLE public.result;

CREATE TABLE public.result
(
    person integer NOT NULL,
    competition integer NOT NULL,
    result integer NOT NULL,
    id integer NOT NULL DEFAULT nextval('result_id_seq'::regclass),
    CONSTRAINT pk_res_id PRIMARY KEY (id),
    CONSTRAINT fk_res_comp FOREIGN KEY (competition)
        REFERENCES public.competition (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_res_pers FOREIGN KEY (person)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.result
    OWNER to postgres;