CREATE TABLE t_order_line_items
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    sku_code VARCHAR(255) NULL,
    price    DECIMAL NULL,
    quantity INT NULL,
    CONSTRAINT pk_t_order_line_items PRIMARY KEY (id)
);

CREATE TABLE t_orders
(
    id           INT AUTO_INCREMENT NOT NULL,
    order_number VARCHAR(255) NULL,
    CONSTRAINT pk_t_orders PRIMARY KEY (id)
);

CREATE TABLE t_orders_order_line_items_list
(
    order_id                 INT    NOT NULL,
    order_line_items_list_id BIGINT NOT NULL
);

ALTER TABLE t_orders_order_line_items_list
    ADD CONSTRAINT uc_t_orders_order_line_items_list_orderlineitemslist UNIQUE (order_line_items_list_id);

ALTER TABLE t_orders_order_line_items_list
    ADD CONSTRAINT fk_tordordlinitelis_on_order FOREIGN KEY (order_id) REFERENCES t_orders (id);

ALTER TABLE t_orders_order_line_items_list
    ADD CONSTRAINT fk_tordordlinitelis_on_order_line_items FOREIGN KEY (order_line_items_list_id) REFERENCES t_order_line_items (id);