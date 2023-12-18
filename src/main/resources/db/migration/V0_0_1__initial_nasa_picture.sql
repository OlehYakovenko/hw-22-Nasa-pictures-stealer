CREATE TABLE IF NOT EXISTS cameras
(
    id bigserial primary key ,
    nasa_id bigint not null ,
    name text,
    created_at timestamp not null default now(),
    CONSTRAINT cameras_nasa_id_uq UNIQUE (nasa_id)
);

CREATE TABLE IF NOT EXISTS pictures
(
    id bigserial,
    nasa_id bigint not null ,
    img_src text not null ,
    camera_id bigint constraint pictures_camera_id_fk REFERENCES cameras(id),
    created_at timestamp not null default now(),
    CONSTRAINT pictures_id_pk PRIMARY KEY (id),
    CONSTRAINT pictures_nasa_id_uq UNIQUE (nasa_id)
);
