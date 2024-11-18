USE `transaction`;

INSERT INTO `user` (`id`, `email`, `balance`, `first_name`, `last_name`, `password`, `profile_image`) VALUES
(1,	'user@nutech-integrasi.com',	0,	'User',	'Nutech',	'abcdef1234',	'');

INSERT INTO `banner` (`banner_name`, `banner_image`, `description`) VALUES
('Banner 1',	'https://nutech-integrasi.app/dummy.jpg',	'Lerem Ipsum Dolor sit amet'),
('Banner 2',	'https://nutech-integrasi.app/dummy.jpg',	'Lerem Ipsum Dolor sit amet'),
('Banner 3',	'https://nutech-integrasi.app/dummy.jpg',	'Lerem Ipsum Dolor sit amet'),
('Banner 4',	'https://nutech-integrasi.app/dummy.jpg',	'Lerem Ipsum Dolor sit amet'),
('Banner 5',	'https://nutech-integrasi.app/dummy.jpg',	'Lerem Ipsum Dolor sit amet'),
('Banner 6',	'https://nutech-integrasi.app/dummy.jpg',	'Lerem Ipsum Dolor sit amet');

INSERT INTO `service` (`service_code`, `service_icon`, `service_name`, `service_tarif`) VALUES
('MUSIK',	'https://nutech-integrasi.app/dummy.jpg',	'Musik Berlangganan',	50000),
('PAJAK',	'https://nutech-integrasi.app/dummy.jpg',	'Pajak PBB',	40000),
('PAKET_DATA',	'https://nutech-integrasi.app/dummy.jpg',	'Paket data',	50000),
('PDAM',	'https://nutech-integrasi.app/dummy.jpg',	'PDAM Berlangganan',	40000),
('PGN',	'https://nutech-integrasi.app/dummy.jpg',	'PGN Berlangganan',	50000),
('PLN',	'https://nutech-integrasi.app/dummy.jpg',	'Listrik',	10000),
('PULSA',	'https://nutech-integrasi.app/dummy.jpg',	'Pulsa',	40000),
('QURBAN',	'https://nutech-integrasi.app/dummy.jpg',	'Qurban',	200000),
('VOUCHER_GAME',	'https://nutech-integrasi.app/dummy.jpg',	'Voucher Game',	100000),
('VOUCHER_MAKANAN',	'https://nutech-integrasi.app/dummy.jpg',	'Voucher Makanan',	100000),
('ZAKAT',	'https://nutech-integrasi.app/dummy.jpg',	'Zakat',	300000);


