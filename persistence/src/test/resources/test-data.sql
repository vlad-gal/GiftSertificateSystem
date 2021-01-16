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