CREATE TABLE report (
    id int primary key NOT NULL,
    experimentId varchar(50) NOT NULL UNIQUE
);


CREATE TABLE image (
    id int primary key NOT NULL,
    report_id int NOT NULL references report(id),
    metric varchar(20) NOT NULL,
    chartTypes varchar(20) NOT NULL,
    imageAsBytea bytea NOT NULL
);


CREATE TABLE experiment (
    id int primary key NOT NULL,
    taskid varchar(50) NOT NULL UNIQUE,
    dispatcher varchar(50) NOT NULL
);


CREATE TABLE result (
    id int primary key NOT NULL,
    measurement varchar(50) NOT NULL
);


CREATE TABLE resultvalue (
    id int primary key NOT NULL,
    timestamp varchar(50) NOT NULL,
    value FLOAT NOT NULL
);


CREATE SEQUENCE report_sequence START 1;
CREATE SEQUENCE image_sequence START 1;
CREATE SEQUENCE experiment_sequence START 1;
CREATE SEQUENCE result_sequence START 1;
CREATE SEQUENCE resultvalue_sequence START 1;


CREATE TABLE experiment_result(
    experiment_id int NOT NULL references experiment(id),
    result_id int NOT NULL references result(id)
)

;
CREATE TABLE result_value(
    result_id int NOT NULL references result(id),
    value_id int NOT NULL references resultvalue(id)
)
;
