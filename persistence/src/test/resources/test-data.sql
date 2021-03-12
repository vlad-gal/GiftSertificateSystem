INSERT INTO gift_certificates (certificateId, name, description, price, duration, create_date,
                               last_update_date)
VALUES (1, 'Полет на дирижабле', 'Незабываемое ощущение', 450, 1, '2021-01-03 00:20:41', '2021-01-03 00:20:41');
INSERT INTO gift_certificates (certificateId, name, description, price, duration, create_date,
                               last_update_date)
VALUES (2, 'Массаж', 'Всего тела', 100, 1, '2021-01-03 00:20:41', '2021-01-03 00:20:41');
INSERT INTO gift_certificates (certificateId, name, description, price, duration, create_date,
                               last_update_date)
VALUES (3, 'Прыжок с парашутом', 'С высоты 10 км', 200, 1, '2021-01-03 00:20:41', '2021-01-03 00:20:41');
INSERT INTO gift_certificates (certificateId, name, description, price, duration, create_date,
                               last_update_date)
VALUES (4, 'Cinema', 'All films during 15 days', 500, 15, '2021-01-03 00:20:41', '2021-01-03 00:20:41');
INSERT INTO gift_certificates (certificateId, name, description, price, duration, create_date,
                               last_update_date)
VALUES (5, 'Туристическое оборудование', 'Купи 10 ледорубов и получи 11 в подарок!', 400, 1, '2021-01-03 00:20:41',
        '2021-01-03 00:20:41');

INSERT INTO tags (tagId, tagName)
VALUES (1, 'Отдых');
INSERT INTO tags (tagId, tagName)
VALUES (2, 'Развлечение');
INSERT INTO tags (tagId, tagName)
VALUES (3, 'Отпуск');
INSERT INTO tags (tagId, tagName)
VALUES (4, 'Туризм');
INSERT INTO tags (tagId, tagName)
VALUES (5, 'Поход');
INSERT INTO tags (tagId, tagName)
VALUES (6, 'Здоровье');
INSERT INTO tags (tagId, tagName)
VALUES (7, 'Экстрим');
INSERT INTO tags (tagId, tagName)
VALUES (8, 'Массаж');

INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (1, 1);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (1, 2);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (1, 7);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (2, 1);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (2, 6);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (2, 8);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (3, 2);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (3, 7);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (4, 1);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (4, 2);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (4, 3);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (5, 1);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (5, 3);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (5, 4);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (5, 5);
INSERT INTO certificates_has_tags (certificateId, tagId)
VALUES (5, 7);

INSERT INTO roles (roleId, roleName)
VALUES (1, 'ADMIN');
INSERT INTO roles (roleId, roleName)
VALUES (2, 'USER');

INSERT INTO users (userId, login, firstName, lastName, password, roleId)
VALUES (1, 'genry', 'Гриша', 'Пупкин', '$2y$12$D5RYYL8r3v7.xoViUgALMetX9YWux53UrKjun0CjvicznCV81V69m', 1);
INSERT INTO users (userId, login, firstName, lastName, password, roleId)
VALUES (2, 'josh', 'Josh', 'Bin', '$2y$12$D5RYYL8r3v7.xoViUgALMetX9YWux53UrKjun0CjvicznCV81V69m', 2);
INSERT INTO users (userId, login, firstName, lastName, password, roleId)
VALUES (3, 'qwe', 'Vlad', 'Gal', '$2y$12$D5RYYL8r3v7.xoViUgALMetX9YWux53UrKjun0CjvicznCV81V69m', 2);
INSERT INTO users (userId, login, firstName, lastName, password, roleId)
VALUES (4, 'john', 'Витя', 'АК', '$2y$12$D5RYYL8r3v7.xoViUgALMetX9YWux53UrKjun0CjvicznCV81V69m', 2);
INSERT INTO users (userId, login, firstName, lastName, password, roleId)
VALUES (5, 'dou', 'Vasya', 'Brant', '$2y$12$D5RYYL8r3v7.xoViUgALMetX9YWux53UrKjun0CjvicznCV81V69m', 2);


INSERT INTO orders (orderId, userId, purchase_date, cost)
VALUES (1, 1, '2021-01-03 00:20:41', 123);
INSERT INTO orders (orderId, userId, purchase_date, cost)
VALUES (2, 1, '2021-01-03 00:20:41', 332);
INSERT INTO orders (orderId, userId, purchase_date, cost)
VALUES (3, 1, '2021-01-03 00:20:41', 1);
INSERT INTO orders (orderId, userId, purchase_date, cost)
VALUES (4, 2, '2021-01-03 00:20:41', 6545);
INSERT INTO orders (orderId, userId, purchase_date, cost)
VALUES (5, 3, '2021-01-03 00:20:41', 5);
INSERT INTO orders (orderId, userId, purchase_date, cost)
VALUES (6, 4, '2021-01-03 00:20:41', 344);

INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (1, 2);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (1, 1);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (2, 3);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (3, 5);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (4, 1);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (1, 3);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (1, 4);
INSERT INTO orders_has_gift_certificate (orderId, certificateId)
VALUES (1, 5);

INSERT INTO permissions (permissionId, permissionName)
VALUES (1, 'gc:read');
INSERT INTO permissions (permissionId, permissionName)
VALUES (2, 'gc:update');
INSERT INTO permissions (permissionId, permissionName)
VALUES (3, 'gc:delete');
INSERT INTO permissions (permissionId, permissionName)
VALUES (4, 'gc:create');
INSERT INTO permissions (permissionId, permissionName)
VALUES (5, 'order:read');
INSERT INTO permissions (permissionId, permissionName)
VALUES (6, 'order:create');
INSERT INTO permissions (permissionId, permissionName)
VALUES (7, 'tag:read');
INSERT INTO permissions (permissionId, permissionName)
VALUES (8, 'tag:create');
INSERT INTO permissions (permissionId, permissionName)
VALUES (9, 'tag:delete');
INSERT INTO permissions (permissionId, permissionName)
VALUES (10, 'actuator:action');
INSERT INTO permissions (permissionId, permissionName)
VALUES (11, 'tag:read-top');
INSERT INTO permissions (permissionId, permissionName)
VALUES (12, 'user:read');

INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (1, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (2, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (3, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (4, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (5, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (6, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (7, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (8, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (9, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (10, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (11, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (12, 1);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (1, 2);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (5, 2);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (6, 2);
INSERT INTO permissions_has_roles (permissionId, roleId)
VALUES (7, 2);
