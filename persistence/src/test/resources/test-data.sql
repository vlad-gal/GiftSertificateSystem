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

INSERT INTO users (userId, login, firstName, lastName)
VALUES (1, 'genry', 'Гриша', 'Пупкин');
INSERT INTO users (userId, login, firstName, lastName)
VALUES (2, 'josh', 'Josh', 'Bin');
INSERT INTO users (userId, login, firstName, lastName)
VALUES (3, 'qwe', 'Vlad', 'Gal');
INSERT INTO users (userId, login, firstName, lastName)
VALUES (4, 'john', 'Витя', 'АК');
INSERT INTO users (userId, login, firstName, lastName)
VALUES (5, 'dou', 'Dick', 'Brant');

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