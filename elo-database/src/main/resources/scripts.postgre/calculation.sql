-- Table: public.calculation

-- DROP TABLE public.calculation;

CREATE TABLE public.calculation
(
    result integer NOT NULL,
    rating double precision NOT NULL,
    change_rating double precision NOT NULL,
    type smallint,
    calculator smallint NOT NULL,
    factor integer NOT NULL,
    total_factor integer NOT NULL,
    id integer NOT NULL DEFAULT nextval('calculation_id_seq'::regclass),
    CONSTRAINT pk_calc_id PRIMARY KEY (id),
    CONSTRAINT fk_calc_res FOREIGN KEY (result)
        REFERENCES public.result (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.calculation
    OWNER to postgres;